package org.wine.productservice.wine.dto

import com.fasterxml.jackson.annotation.JsonInclude
import org.wine.productservice.wine.entity.Wine
import java.io.Serializable

@JsonInclude(JsonInclude.Include.NON_NULL)
data class WineResponseDto(
    val wineId: Long,
    val name: String,
    val description: String,
    val region: RegionDto,
    val category: List<CategoryDto>,
) : Serializable {
        companion object {
            fun fromResponseDtoWine(wine: Wine): WineResponseDto {
                return WineResponseDto(
                    wineId = wine.wineId,
                    name = wine.name,
                    description = wine.description,
                    region = wine.region.let { RegionDto.fromResponseDtoRegion(it) },
                    category = wine.categories.map { CategoryDto.fromCategory(it) }
                )
            }
        }
}
