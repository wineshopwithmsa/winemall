package org.wine.orderservice.order.transaction.event

data class OrderCreateEvent(
    val orderId : Long,
    val customerId : Long,
    val winSaleId : Long,
    val couponId : Long,
    val quantity : Int
)
