package org.wine.orderorchestrator.orderorchestrator.order.transcation.event

data class CheckStockCompletedEvent(
    val orderId : Long,
    val totalPrice: Int
)