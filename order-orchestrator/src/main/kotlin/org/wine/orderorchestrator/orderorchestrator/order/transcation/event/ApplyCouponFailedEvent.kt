package org.wine.orderorchestrator.orderorchestrator.order.transcation.event

data class ApplyCouponFailedEvent(
    val orderId : Long,
    val failureMessage: String
)