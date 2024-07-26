package org.wine.productservice.wine.dto

import com.fasterxml.jackson.annotation.JsonInclude
import org.wine.productservice.wine.entity.SaleStatus
import org.wine.productservice.wine.entity.WineSale
import java.io.Serializable
import java.time.Instant

@JsonInclude(JsonInclude.Include.NON_NULL)
data class WineSaleDto(
    val id: Long,
    val sellerId: Long,
    val wineId: Long,
    val price : Int,
    val registeredQuantity: Int,
    val stockQuantity: Int,
    val soldQuantity: Int,
    val saleStatus: SaleStatus,
    val saleStartTime: Instant,
    val saleEndTime: Instant,
): Serializable {
    companion object {
        fun fromSaleWine(wineSale: WineSale): WineSaleDto {
            return WineSaleDto(
                id = wineSale.wineSaleId,
                price = wineSale.price,
                sellerId = wineSale.sellerId,
                wineId = wineSale.wineId,
                registeredQuantity = wineSale.registeredQuantity,
                stockQuantity = wineSale.stockQuantity,
                soldQuantity = wineSale.soldQuantity,
                saleStatus = wineSale.saleStatus,
                saleStartTime = wineSale.saleStartTime,
                saleEndTime = wineSale.saleEndTime,
            )
        }
    }
}