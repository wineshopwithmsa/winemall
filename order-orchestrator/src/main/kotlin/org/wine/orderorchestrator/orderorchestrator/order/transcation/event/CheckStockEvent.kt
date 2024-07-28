package org.wine.orderorchestrator.orderorchestrator.order.transcation.event

import org.wine.orderorchestrator.orderorchestrator.order.dto.OrderDto


data class CheckStockEvent(
    val orderId : Long,
    val wineOrderList : List<OrderDto>
)
