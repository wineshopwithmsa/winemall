package org.wine.orderservice.order.dto.request


data class OrderRequestDto(
    val orderId : Long,
    val wineSaleId : Long,
    val couponId : Long,
    val memberId : Long,
    val quantity : Int
)