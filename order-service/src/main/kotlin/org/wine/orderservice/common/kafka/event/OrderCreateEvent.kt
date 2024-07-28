package org.wine.orderservice.common.kafka.event

import org.wine.orderservice.order.dto.OrderDto

data class OrderCreateEvent(
    val orderId : Long,
    val wineList : List<OrderDto>,
    val couponId : Long,
    val memberId : Long
)
