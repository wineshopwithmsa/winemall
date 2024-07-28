package org.wine.userservice.common.kafka.event

data class ApplyCouponFailedEvent(
    val orderId : Long,
    val failureMessage: String
)