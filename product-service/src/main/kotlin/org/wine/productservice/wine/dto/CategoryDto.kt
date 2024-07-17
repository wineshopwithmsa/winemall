package org.wine.productservice.wine.dto

import org.wine.productservice.wine.entity.Category
import java.io.Serializable

data class CategoryDto(
    val categoryId: Long,
    val name: String,
) : Serializable {
    companion object {
        fun fromCategory(category: Category): CategoryDto {
            return CategoryDto(
                categoryId = category.categoryId,
                name = category.name
            )
        }
    }
}
