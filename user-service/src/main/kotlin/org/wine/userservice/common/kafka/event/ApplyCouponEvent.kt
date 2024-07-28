package org.wine.userservice.common.kafka.event

data class ApplyCouponEvent(
    val orderId : Long,
    val couponId : Long,
    val memberId : Long
)