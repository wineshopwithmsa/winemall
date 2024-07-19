package org.wine.productservice.wine.dto

import io.swagger.v3.oas.annotations.media.Schema
import org.wine.productservice.wine.entity.Region
import java.io.Serializable

data class RegionDto (
    @field:Schema(description = "지역 ID", example = "1")
    val id: Long,
    @field:Schema(description = "지역 이름", example = "region1")
    val name: String,
) : Serializable {
    companion object {
        fun fromRegion(region: Region): RegionDto {
            return RegionDto(
                id = region.regionId,
                name = region.name
            )
        }
    }
}
