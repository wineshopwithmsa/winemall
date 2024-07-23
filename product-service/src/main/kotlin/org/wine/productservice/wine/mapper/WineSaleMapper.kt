package org.wine.productservice.wine.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.wine.productservice.wine.dto.WineSaleDto
import org.wine.productservice.wine.entity.WineSale

@Mapper(componentModel = "spring")
interface WineSaleMapper {
    @Mapping(source = "wineId", target = "id")
    fun toWineSaleDto(wineSale: WineSale): WineSaleDto
}