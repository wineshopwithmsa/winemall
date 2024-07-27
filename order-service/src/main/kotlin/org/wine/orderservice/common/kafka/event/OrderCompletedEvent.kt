package org.wine.orderservice.common.kafka.event

data class OrderCompletedEvent(
    val orderId : Long
)