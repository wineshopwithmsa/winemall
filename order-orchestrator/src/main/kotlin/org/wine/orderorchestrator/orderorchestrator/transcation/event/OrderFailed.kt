package org.wine.orderorchestrator.orderorchestrator.transcation.event

data class OrderFailed(
    val orderId : Long,
    val message : String
)
