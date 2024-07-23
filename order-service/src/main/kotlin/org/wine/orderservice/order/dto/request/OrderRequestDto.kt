package org.wine.orderservice.order.dto.request


import org.wine.orderservice.order.dto.OrderDto
import org.wine.orderservice.order.entity.Order
import org.wine.orderservice.order.entity.OrderStatus
import java.util.Date


data class OrderRequestDto(

    //ORDER COLUMN
    val receiverPhoneNumber : String,
    val receiverName : String,
    val receiverAddr : String,
    val receiverAddrDetail : String,
    val totalPrice : Int,
    val finalPrice : Int,
    //EXTRA
    val wineList : List<OrderDto>,
    val couponId : Long,
    val memberId : Long
){
    fun toEntity(rsrvDate : String) : Order {
        return Order(
            receiverPhoneNumber = receiverPhoneNumber,
            receiverName = receiverName,
            receiverAddr = receiverAddr,
            receiverAddrDetail = receiverAddrDetail,
            totalPrice = totalPrice,
            finalPrice = finalPrice,
            rsrvDate = rsrvDate,
            status = OrderStatus.ORDER_REQUEST,
        )
    }
}
