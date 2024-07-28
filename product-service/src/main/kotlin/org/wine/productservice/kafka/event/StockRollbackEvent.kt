package org.wine.productservice.kafka.event

import org.wine.productservice.wine.dto.OrderDto

data class StockRollbackEvent(
    val orderId : Long,
    val wineOrderList : List<OrderDto>,
    val failureMessage: String
)
