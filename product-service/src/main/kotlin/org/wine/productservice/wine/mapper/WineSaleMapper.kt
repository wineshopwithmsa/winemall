package org.wine.productservice.wine.mapper

import org.mapstruct.Mapper
import org.wine.productservice.wine.dto.WineSaleCreateRequestDto
import org.wine.productservice.wine.dto.WineSaleDto
import org.wine.productservice.wine.entity.WineSale

@Mapper(componentModel = "spring")
interface WineSaleMapper {
    fun toWineSaleDto(wineSale: WineSale): WineSaleDto
    fun toWineSale(wineSaleDto: WineSaleCreateRequestDto): WineSale {
        val wineSale = WineSale(
            wineId = wineSaleDto.wineId,
            price = wineSaleDto.price,
            saleStartTime = wineSaleDto.saleStartTime,
            saleEndTime = wineSaleDto.saleEndTime,
        )
        wineSale.prepareSaleQuantities(wineSaleDto.quantity)

        return wineSale
    }
}