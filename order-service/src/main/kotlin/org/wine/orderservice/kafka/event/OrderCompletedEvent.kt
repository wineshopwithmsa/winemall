package org.wine.orderservice.kafka.event

data class OrderCompletedEvent(
    val orderId : Long
)