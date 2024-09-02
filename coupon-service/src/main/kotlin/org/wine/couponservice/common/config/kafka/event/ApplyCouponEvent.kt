package org.wine.couponservice.common.config.kafka.event

data class ApplyCouponEvent(
    val orderId : Long,
    val couponId : Long,
    val memberId : Long
)