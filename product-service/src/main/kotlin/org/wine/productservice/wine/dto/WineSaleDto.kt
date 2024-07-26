package org.wine.productservice.wine.dto

import org.wine.productservice.wine.entity.WineSale
import java.io.Serializable

data class WineSaleDto(
    val wineSaleId: Long,
    val price : Int
): Serializable {
    companion object {
        fun fromSaleWine(wine: WineSale): WineSaleDto {
            return WineSaleDto(
                wineSaleId = wine.wineSaleId,
                price = wine.price
            )
        }
    }
}