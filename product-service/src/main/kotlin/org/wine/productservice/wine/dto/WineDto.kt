package org.wine.productservice.wine.dto

import com.fasterxml.jackson.annotation.JsonInclude
import io.swagger.v3.oas.annotations.media.Schema
import org.wine.productservice.wine.entity.Wine
import java.io.Serializable
import java.math.BigDecimal

@JsonInclude(JsonInclude.Include.NON_NULL)
data class WineDto(
    @field:Schema(description = "와인 ID", example = "1")
    val id: Long,
    @field:Schema(description = "와인 이름", example = "와인이름")
    val name: String,
    @field:Schema(description = "와인 설명", example = "와인 설명")
    val description: String,
    @field:Schema(description = "알콜 도수", example = "12.5")
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
