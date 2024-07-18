package org.wine.productservice.wine.dto

import com.fasterxml.jackson.annotation.JsonInclude
import org.wine.productservice.wine.entity.Wine
import java.io.Serializable
import java.math.BigDecimal

@JsonInclude(JsonInclude.Include.NON_NULL)
data class WineDto(
    val id: Long,
    val name: String,
    val description: String,
    val alcoholPercentage: BigDecimal,
    val region: RegionDto?,
    val categories: Set<CategoryDto>?,
) : Serializable {
        companion object {
            fun fromWine(wine: Wine): WineDto {
                return WineDto(
                    id = wine.wineId,
                    name = wine.name,
                    description = wine.description,
                    alcoholPercentage = wine.alcoholPercentage,
                    region = wine.region.let { RegionDto.fromRegion(it) },
                    categories = wine.categories.map { CategoryDto.fromCategory(it.category) }.toSet()
                )
            }
        }
}
