package org.wine.orderorchestrator.orderorchestrator.transcation.event

import org.wine.orderorchestrator.orderorchestrator.order.dto.OrderDto

data class OrderCreateEvent(
    val orderId : Long,
    val wineList : List<OrderDto>,
    val couponId : Long,
    val memberId : Long
)