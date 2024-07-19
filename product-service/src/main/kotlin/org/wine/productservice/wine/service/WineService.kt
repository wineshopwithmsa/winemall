package org.wine.productservice.wine.service

import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.wine.productservice.auth.AuthService
import org.wine.productservice.wine.dto.WineCreateRequestDto
import org.wine.productservice.wine.dto.WineDto
import org.wine.productservice.wine.dto.WineUpdateRequestDto
import org.wine.productservice.wine.entity.Category
import org.wine.productservice.wine.entity.WineCategory
import org.wine.productservice.wine.exception.*
import org.wine.productservice.wine.mapper.WineMapper
import org.wine.productservice.wine.repository.CategoryRepository
import org.wine.productservice.wine.repository.RegionRepository
import org.wine.productservice.wine.repository.WineCategoryRepository
import org.wine.productservice.wine.repository.WineRepository

@Service
class WineService @Autowired constructor(
    private val wineRepository: WineRepository,
    private val wineMapper: WineMapper,
    private val regionRepository: RegionRepository,
    private val categoryRepository: CategoryRepository,
    private val wineCategoryRepository: WineCategoryRepository,
    private val authService: AuthService,
) {
    fun getWine(wineId: Long): WineDto {
        var wine = wineRepository.findById(wineId)
            .orElseThrow { WineNotFoundException(wineId)}
        return wineMapper.toWineDto(wine)
    }

    fun getWines(): List<WineDto> {
        return wineRepository.findAll().map { wine -> WineDto.fromWine(wine) }
    }

    @Transactional
    fun addWine(wineDto: WineCreateRequestDto): WineDto {
        val userId = authService.getAccountId()

        val region = regionRepository.findById(wineDto.regionId).
                    orElseThrow { InvalidRegionException(wineDto.regionId) }
        val categories = getCategoriesFromIds(wineDto.categoryIds)
        val wine = wineMapper.toWine(wineDto, region)
        wine.registrantId = userId
        wine.tagWithCategories(categories)

        val savedWine = wineRepository.save(wine)

        return wineMapper.toWineDto(savedWine)
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

    @Transactional
    fun updateWine(wineId: Long, requestDto: WineUpdateRequestDto): WineDto {
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
        var res = wineMapper.toWineDto(savedWine)
        return res
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