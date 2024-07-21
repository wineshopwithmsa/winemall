package org.wine.orderservice.order.entity

enum class OrderStatus(
    val status: Int,
    val description: String ) {
    ORDER_REQUEST(0, "주문 진행중"),
    ORDER_COMPLETED(1, "주문 완료"),
    ORDER_FAILED(2, "주문 실패"),
    ORDER_CONFIRMED(3, "주문 확정")
}