package org.wine.productservice.wine.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.wine.productservice.wine.dto.RegionDto
import org.wine.productservice.wine.entity.Region

@Mapper(componentModel = "spring")
interface RegionMapper {
    @Mapping(source = "regionId", target = "id")
    fun toRegionDto(region: Region): RegionDto
}

