package org.wine.productservice.wine.dto

import org.wine.productservice.wine.entity.Category
import java.io.Serializable

data class CategoryDto(
    val id: Long,
    val name: String,
) : Serializable {
    companion object {
        fun fromCategory(category: Category): CategoryDto {
            return CategoryDto(
                id = category.categoryId,
                name = category.name
            )
        }
    }
}
