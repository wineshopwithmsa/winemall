package org.wine.userservice.common.kafka.event

data class ApplyCouponCompletedEvent(
    val orderId : Long
)