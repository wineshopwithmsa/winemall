package org.wine.productservice.wine.testutil

import jakarta.persistence.Id
import org.wine.productservice.wine.dto.WineCreateRequestDto
import org.wine.productservice.wine.entity.Category
import org.wine.productservice.wine.entity.Region
import org.wine.productservice.wine.entity.Wine
import org.wine.productservice.wine.entity.WineCategory
import java.math.BigDecimal

object TestData {
    // entity
    fun createCategory(
        id: Long = 1L,
        name: String = "White Wine"
    ) = Category(categoryId = id, name = name)

    fun createRegion(
        id: Long = 1L,
        name: String = "Napa Valley"
    ) = Region(regionId = id, name = name)

    fun createWine(
        id: Long = 1L,
        name: String = "Chardonnay",
        region: Region = createRegion(),
        description: String = "Description",
        alcoholPercentage: BigDecimal = BigDecimal("13.5")
    ) = Wine(
        wineId = id,
        name = name,
        region = region,
        description = description,
        alcoholPercentage = alcoholPercentage
    )

    fun createWineCategory(
        id: Long = 1L,
        wine: Wine = createWine(),
        category: Category = createCategory()
    ) = WineCategory(
        wineCategoryId = id,
        wine = wine,
        category = category,
    )

    // dto
    fun createWineCreateRequestDto(
        name: String = "Chardonnay",
        description: String = "A crisp white wine",
        alcoholPercentage: BigDecimal = BigDecimal("13.0"),
        regionId: Long = 1L,
        categoryIds: Set<Long> = setOf(1L, 2L)
    ) = WineCreateRequestDto (
        name = name,
        description = description,
        alcoholPercentage = alcoholPercentage,
        regionId = regionId,
        categoryIds = categoryIds
    )
}