package org.wine.productservice.wine.mapper

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.wine.productservice.wine.entity.WineCategory
import org.wine.productservice.wine.testutil.TestData

@ExtendWith(SpringExtension::class)
@SpringBootTest
class WineMapperTest {

    @Autowired
    private lateinit var wineMapper: WineMapper

    @Autowired
    private lateinit var categoryMapper: CategoryMapper

    @Autowired
    private lateinit var regionMapper: RegionMapper

    @Test
    fun `Wine to WineDto`() {
        // Given
        val category1 = TestData.createCategory(id = 1L, name = "Sparkling")
        val category2 = TestData.createCategory(id = 2L, name = "Rosé")
        val categories = setOf(
            TestData.createWineCategory(id = 1L, category = category1),
            TestData.createWineCategory(id = 2L, category = category2)
        )
        val wine = TestData.createWine().apply {
            this.category = categories as MutableSet<WineCategory>
        }

        // When
        val wineDto = wineMapper.toWineDto(wine)

        // Then
        assertEquals(wine.wineId, wineDto.id)
        assertEquals(wine.name, wineDto.name)
        assertEquals(wine.description, wineDto.description)
        assertEquals(wine.alcoholPercentage, wineDto.alcoholPercentage)
        assertEquals(regionMapper.toRegionDto(wine.region), wineDto.region)
        assertEquals(
            categories.map { categoryMapper.fromWineCategoryToCategoryDto(it) }.toSet(),
            wineDto.categories
        )
    }

    @Test
    fun `toWine should correctly map WineCreateRequestDto to Wine`() {
        // Given
        val region = TestData.createRegion()
        val wineCreateRequestDto = TestData.createWineCreateRequestDto()

        // When
        val wine = wineMapper.toWine(wineCreateRequestDto, region)

        // Then
        assertEquals(wineCreateRequestDto.name, wine.name)
        assertEquals(wineCreateRequestDto.description, wine.description)
        assertEquals(wineCreateRequestDto.alcoholPercentage, wine.alcoholPercentage)
        assertEquals(region, wine.region)
    }

}