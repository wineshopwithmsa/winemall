package org.wine.orderorchestrator.orderorchestrator.order.transcation.event

data class OrderRollbackEvent(
    val orderId : Long
)