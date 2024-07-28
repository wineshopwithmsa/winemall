package org.wine.orderorchestrator.orderorchestrator.order.transcation.event

import org.wine.orderorchestrator.orderorchestrator.order.dto.OrderDto

data class CheckStockFailedEvent(
    val wineOrderList : List<OrderDto>
)