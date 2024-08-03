package org.wine.orderservice.order.dto.request

data class OrderWineRequestDto (
    val wineSaleId : Long,
    val quantity : Int,
    val originPrice : Long,
    val salePrice : Long
)
