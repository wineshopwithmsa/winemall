package org.wine.productservice.wine.mapper

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.wine.productservice.wine.entity.Category
import org.wine.productservice.wine.testutil.TestData

@ExtendWith(SpringExtension::class)
@SpringBootTest
class CategoryMapperTest {

    @Autowired
    private lateinit var categoryMapper: CategoryMapper

    @Test
    fun `Category to CategoryDto`() {
        // Given
        val category = Category(categoryId = 1L, name = "Red Wine")

        // When
        val categoryDto = categoryMapper.toCategoryDto(category)

        // Then
        assertEquals(category.categoryId, categoryDto.id)
        assertEquals(category.name, categoryDto.name)
    }

    @Test
    fun `WineCategory to CategoryDto`() {
        // Given
        val category = TestData.createCategory()
        val wine = TestData.createWine()
        val wineCategory = TestData.createWineCategory()

        // When
        val categoryDto = categoryMapper.fromWineCategoryToCategoryDto(wineCategory)

        // Then
        assertEquals(category.categoryId, categoryDto.id)
        assertEquals(category.name, categoryDto.name)
    }

    @Test
    fun `WineCategorySet to CategoryDtoSet`() {
        // Given
        val category1 = TestData.createCategory(id = 1L, name = "Sparkling")
        val category2 = TestData.createCategory(id = 2L, name = "Rosé")
        val wine = TestData.createWine()
        val wineCategories = setOf(
            TestData.createWineCategory(id = 2L, wine = wine, category = category1),
            TestData.createWineCategory(id = 2L, wine = wine, category = category2),
        )

        // When
        val categoryDtos = categoryMapper.toCategoryDtoSet(wineCategories)

        // Then
        assertEquals(2, categoryDtos.size)
        assertTrue(categoryDtos.any { it.id == category1.categoryId && it.name == category1.name })
        assertTrue(categoryDtos.any { it.id == category2.categoryId && it.name == category2.name })
    }
}