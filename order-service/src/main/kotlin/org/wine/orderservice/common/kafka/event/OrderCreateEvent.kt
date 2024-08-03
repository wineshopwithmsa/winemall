package org.wine.orderservice.common.kafka.event

import org.wine.orderservice.order.dto.request.OrderWineRequestDto

data class OrderCreateEvent(
    val orderId : Long,
    val wineOrderList : List<OrderWineRequestDto>,
    val couponId : Long? = null,
    val memberId : Long
)
