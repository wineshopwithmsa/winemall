package org.wine.orderorchestrator.orderorchestrator.order.transcation.event

data class ApplyCouponEvent(
    val orderId : Long,
    val couponId : Long? = null,
    val memberId : Long
)