package org.wine.orderservice.order.dto.request


import org.wine.orderservice.order.dto.OrderDto

data class OrderPriceRequestDto(
    val productList: List<OrderDto>,
    val couponID : Long
)
