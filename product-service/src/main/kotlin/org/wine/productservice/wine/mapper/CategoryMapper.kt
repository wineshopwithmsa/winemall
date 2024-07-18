package org.wine.productservice.wine.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.wine.productservice.wine.dto.CategoryDto
import org.wine.productservice.wine.entity.Category
import org.wine.productservice.wine.entity.WineCategory

@Mapper(componentModel = "spring")
interface CategoryMapper {
    fun toCategoryDto(category: Category): CategoryDto

    @Mapping(source = "category", target = ".")
    @Mapping(source = "category.categoryId", target = "id")
    fun fromWineCategory(wineCategory: WineCategory): CategoryDto

    fun toCategoryDtoSet(wineCategories: Set<WineCategory>): Set<CategoryDto> {
        return wineCategories.map { fromWineCategory(it) }.toSet()
    }
}

