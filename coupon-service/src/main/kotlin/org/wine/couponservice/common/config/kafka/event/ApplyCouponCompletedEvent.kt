package org.wine.couponservice.common.config.kafka.event

data class ApplyCouponCompletedEvent(
    val orderId : Long
)