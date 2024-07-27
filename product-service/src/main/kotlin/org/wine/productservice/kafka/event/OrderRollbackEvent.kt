package org.wine.productservice.kafka.event

data class OrderRollbackEvent(
    val orderId : Long
)