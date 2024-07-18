package org.wine.productservice.wine.service

import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.wine.productservice.auth.AuthService
import org.wine.productservice.wine.exception.RegionNotFoundException
import org.wine.productservice.wine.dto.WineRequestDto
import org.wine.productservice.wine.dto.WineDto
import org.wine.productservice.wine.entity.Category
import org.wine.productservice.wine.exception.CategoryNotFoundException
import org.wine.productservice.wine.mapper.WineMapper
import org.wine.productservice.wine.repository.CategoryRepository
import org.wine.productservice.wine.repository.RegionRepository
import org.wine.productservice.wine.repository.WineRepository

@Service
class WineService @Autowired constructor(
    private val wineRepository: WineRepository,
    private val wineMapper: WineMapper,
    private val regionRepository: RegionRepository,
    private val categoryRepository: CategoryRepository,
    private val authService: AuthService,
) {
    fun getWinesForSeller(): List<WineDto> {
        return wineRepository.findAll().map { wine -> WineDto.fromWine(wine) }
    }

    @Transactional
    fun addWine(wineDto: WineRequestDto): WineDto {
        val userId = authService.getAccountId()

        val region = regionRepository.findById(wineDto.regionId).
                    orElseThrow { RegionNotFoundException("Region not found with id: ${wineDto.regionId}") }
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
            throw CategoryNotFoundException("Categories not found with ids: $missingIds")
        }

        return categories.toSet()
    }
}