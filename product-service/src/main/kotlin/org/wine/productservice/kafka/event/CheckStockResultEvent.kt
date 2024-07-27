package org.wine.productservice.kafka.event

data class CheckStockCompleted(
    val totalPrice: Int
)

data class CheckStockFailed(
    val failureReason: String
)