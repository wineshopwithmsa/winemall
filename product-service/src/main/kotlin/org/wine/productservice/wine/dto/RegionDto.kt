package org.wine.productservice.wine.dto

import org.wine.productservice.wine.entity.Region
import java.io.Serializable


data class RegionDto (
    val regionId: Long,
    val name: String,
) : Serializable {
    companion object {
        fun fromResponseDtoRegion(region: Region): RegionDto {
            return RegionDto(
                regionId = region.regionId,
                name = region.name
            )
        }
    }


}
