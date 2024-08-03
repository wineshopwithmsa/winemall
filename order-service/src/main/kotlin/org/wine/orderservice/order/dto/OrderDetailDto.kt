package org.wine.orderservice.order.dto

import jakarta.persistence.Column

data class OrderDetailDto(
    val orderDetailId : Long,
    val orderId : Long,
    val wineSaleId : Long,
    val quantity : Int,
    val originPrice : Long,
    val salePrice : Long
)