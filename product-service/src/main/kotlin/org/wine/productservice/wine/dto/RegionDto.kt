package org.wine.productservice.wine.dto

import org.wine.productservice.wine.entity.Region
import java.io.Serializable


data class RegionDto (
    val id: Long,
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
