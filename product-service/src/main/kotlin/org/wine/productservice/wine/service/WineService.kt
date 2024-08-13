package org.wine.productservice.wine.service

import jakarta.persistence.criteria.JoinType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import org.wine.productservice.wine.dto.*
import org.wine.productservice.wine.entity.Category
import org.wine.productservice.wine.entity.Wine
import org.wine.productservice.wine.entity.WineCategory
import org.wine.productservice.wine.exception.*
import org.wine.productservice.wine.mapper.WineMapper
import org.wine.productservice.wine.mapper.WinePaginationMapper
import jakarta.persistence.criteria.Predicate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.http.HttpHeaders
import org.springframework.transaction.support.TransactionTemplate
import org.wine.productservice.auth.AuthService
import org.wine.productservice.wine.repository.*
import javax.swing.plaf.synth.Region

@Service
class WineService @Autowired constructor(
    private val authService: AuthService,
    private val wineMapper: WineMapper,
    private val winePaginationMapper: WinePaginationMapper,

    private val wineRepository: WineRepository,
    private val regionRepository: RegionRepository,
    private val categoryRepository: CategoryRepository,
    private val wineCategoryRepository: WineCategoryRepository,
    private val transactionTemplate: TransactionTemplate,
) {
    suspend fun getWine(wineId: Long): WineDto = withContext(Dispatchers.IO){
        var wine = wineRepository.findByIdWithCategories(wineId)
            .orElseThrow { WineNotFoundException(wineId)}

        wineMapper.toWineDto(wine)
    }

    suspend fun getWines(
        page: Int,
        perPage: Int,
        regionIds: List<Long>? = null,
        categoryIds: List<Long>? = null
    ): PaginatedWineResponseDto = withContext(Dispatchers.IO) {
        transactionTemplate.execute {
            val pageable = PageRequest.of(page - 1, perPage)
            val specification = createWineSpecification(regionIds, categoryIds)
            val winesPage = wineRepository.findAll(specification, pageable)
            winePaginationMapper.toPaginatedWineResponse(winesPage, "/api/wines")
        } ?: throw IllegalStateException("Transaction failed to execute")
    }

    private fun createWineSpecification(regionIds: List<Long>?, categoryIds: List<Long>?): Specification<Wine> {
        return Specification { root, _, criteriaBuilder ->
            val predicates = mutableListOf<Predicate>()

            regionIds?.let { ids ->
                predicates.add(root.get<Region>("region").get<Long>("id").`in`(ids))
            }

            categoryIds?.let { ids ->
                val joinWineCategory = root.join<Wine, WineCategory>("categories", JoinType.LEFT)
                val joinCategory = joinWineCategory.join<WineCategory, Category>("category", JoinType.LEFT)
                predicates.add(joinCategory.get<Long>("id").`in`(ids))
            }

            criteriaBuilder.and(*predicates.toTypedArray())
        }
    }

    suspend fun addWine(wineDto: WineCreateRequestDto, headers: HttpHeaders): WineDto = withContext(Dispatchers.IO) {
        withContext(Dispatchers.IO) {
            transactionTemplate.execute { status ->
                try {
                    val userId = authService.getAccountId(headers)
                    val region = regionRepository.findById(wineDto.regionId)
                        .orElseThrow { InvalidRegionException(wineDto.regionId) }

                    // suspend 함수를 직접 호출
                    val categories = getCategoriesFromIds(wineDto.categoryIds)

                    val wine = wineMapper.toWine(wineDto, region)
                    wine.registrantId = userId
                    wine.tagWithCategories(categories)

                    val savedWine = wineRepository.save(wine)
                    wineMapper.toWineDto(savedWine)
                } catch (e: Exception) {
                    status.setRollbackOnly()
                    throw e
                }
            } ?: throw RuntimeException("Transaction failed")
        }
    }

    private fun getCategoriesFromIds(categoryIds: Set<Long>?): Set<Category> {
        if (categoryIds.isNullOrEmpty()) {
            return emptySet()
        }

        val categories = categoryRepository.findAllById(categoryIds)
        if (categories.size != categoryIds.size) {
            val foundIds = categories.map { it.categoryId }.toSet()
            val missingIds = categoryIds - foundIds
            throw InvalidCategoryException(missingIds)
        }

        return categories.toSet()
    }

    suspend fun updateWine(wineId: Long, requestDto: WineUpdateRequestDto): WineDto = withContext(Dispatchers.IO) {
        transactionTemplate.execute { status ->
            try {
                validateWineUpdateRequestDto(requestDto)

                val wine = wineRepository.findById(wineId)
                    .orElseThrow { WineNotFoundException(wineId) }

                requestDto.categoryIds?.let { newCategoryIds ->
                    val newCategories = getCategoriesFromIds(newCategoryIds)

                    // 기존 카테고리 중 새로운 카테고리 목록에 없는 것들 삭제
                    val categoriesToRemove = wine.categories.filter { existingCategory ->
                        newCategories.none { it.categoryId == existingCategory.category.categoryId }
                    }
                    wineCategoryRepository.deleteAll(categoriesToRemove)
                    wine.categories -= categoriesToRemove.toSet()

                    newCategories.forEach { category ->
                        if (wine.categories.none { it.category.categoryId == category.categoryId }) {
                            val newWineCategory = WineCategory(wine = wine, category = category)
                            wine.categories += newWineCategory
                        }
                    }
                }

                requestDto.regionId?.let { regionId ->
                    val region = regionRepository.findById(regionId)
                        .orElseThrow { InvalidRegionException(regionId) }
                    wine.region = region
                }

                requestDto.categoryIds?.let { newCategoryIds ->
                    val newCategories = getCategoriesFromIds(newCategoryIds)
                    wine.tagWithCategories(newCategories)
                }

                requestDto.name?.let { wine.name = it }
                requestDto.description?.let { wine.description = it }
                requestDto.alcoholPercentage?.let { wine.alcoholPercentage = it }

                val savedWine = wineRepository.save(wine)
                wineMapper.toWineDto(savedWine)
            } catch (e: Exception) {
                status.setRollbackOnly()
                throw e
            }
        } ?: throw RuntimeException("Transaction failed")
    }

    fun validateWineUpdateRequestDto(requestDto: WineUpdateRequestDto) {
        requestDto.name?.let {
            if (it.isBlank()) {
                throw InvalidRequestException(WineValidationMessages.NAME_BLANK)
            }
            if (it.length < 2 || it.length > 100) {
                throw InvalidRequestException(WineValidationMessages.NAME_LENGTH)
            }
        }

        requestDto.description?.let {
            if (it.isBlank()) {
                throw InvalidRequestException(WineValidationMessages.DESCRIPTION_BLANK)
            }
            if (it.length > 5000) {
                throw InvalidRequestException(WineValidationMessages.DESCRIPTION_LENGTH)
            }
        }
    }
}