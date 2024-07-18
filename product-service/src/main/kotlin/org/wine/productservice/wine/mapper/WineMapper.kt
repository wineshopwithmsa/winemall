package org.wine.productservice.wine.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.wine.productservice.wine.dto.WineRequestDto
import org.wine.productservice.wine.dto.WineDto
import org.wine.productservice.wine.entity.Region
import org.wine.productservice.wine.entity.Wine

@Mapper(componentModel = "spring", uses = [CategoryMapper::class, RegionMapper::class])
interface WineMapper {
    @Mapping(source = "wineId", target = "id")
    fun toWineDto(wine: Wine): WineDto

    fun toWine(wineRequestDto: WineRequestDto, region: Region): Wine {
        val wine = Wine(
            name = wineRequestDto.name,
            description = wineRequestDto.description,
            alcoholPercentage = wineRequestDto.alcoholPercentage,
            region = region,
        )

        return wine
    }
}
