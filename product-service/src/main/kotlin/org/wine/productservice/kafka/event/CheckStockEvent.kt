package org.wine.productservice.kafka.event

import org.wine.productservice.wine.dto.OrderDto

data class CheckStockEvent(
    val orderId : Long,
    val wineOrderList : List<OrderDto>
)