package org.wine.productservice.wine.dto

import io.swagger.v3.oas.annotations.media.Schema
import org.wine.productservice.wine.entity.Category
import java.io.Serializable

data class CategoryDto(
    @field:Schema(description = "카테고리 ID", example = "1")
    val id: Long,
    @field:Schema(description = "카테고리 이름", example = "카테고리이름")
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
