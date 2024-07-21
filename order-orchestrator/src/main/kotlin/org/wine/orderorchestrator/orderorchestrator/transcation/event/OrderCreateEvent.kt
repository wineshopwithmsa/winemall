package org.wine.orderorchestrator.orderorchestrator.transcation.event

data class OrderCreateEvent(
    val orderId : Long,
    val customerId : Long,
    val winSaleId : Long,
    val couponId : Long,
    val quantity : Int
)