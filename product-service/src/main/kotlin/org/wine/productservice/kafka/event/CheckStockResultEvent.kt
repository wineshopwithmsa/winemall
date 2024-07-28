package org.wine.productservice.kafka.event

data class CheckStockCompleted(
    val orderId: Long,
    val totalPrice: Int
)

data class CheckStockFailed(
    val orderId: Long,
    val failureReason: String
)