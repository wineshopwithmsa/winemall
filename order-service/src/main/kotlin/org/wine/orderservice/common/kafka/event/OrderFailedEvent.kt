package org.wine.orderservice.common.kafka.event

data class OrderFailedEvent(
    val orderId : Long,
    val failureMessage: String
)