package org.wine.couponservice.common.config.kafka.event

data class ApplyCouponFailedEvent(
    val orderId : Long,
    val failureMessage: String
)