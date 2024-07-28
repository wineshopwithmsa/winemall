package org.wine.orderservice.order.dto.request


import jakarta.validation.constraints.NotNull
import org.wine.orderservice.order.dto.OrderDto
import org.wine.orderservice.order.entity.Order
import org.wine.orderservice.order.entity.OrderStatus
import java.util.Date


data class OrderRequestDto(

    @field:NotNull(message = "수령자 핸드폰번호 입력은 필수입니다.")
    val receiverPhoneNumber : String,

    @field:NotNull(message = "수령자 이름 입력은 필수입니다.")
    val receiverName : String,

    @field:NotNull(message = "수령자 주소 입력은 필수입니다.")
    val receiverAddr : String,

    @field:NotNull(message = "수령자 상세 주소 입력은 필수입니다.")
    val receiverAddrDetail : String,

    @field:NotNull(message = "주문 총 금액 입력은 필수입니다.")
    val totalPrice : Int,

    @field:NotNull(message = "주문 최종 금액 입력은 필수입니다.")
    val finalPrice : Int,

    val wineList : List<OrderDto>,

    val rsrvDate: String? = null,

    val couponId : Long? = null
){
    fun toEntity(memberId: Long) : Order {
        return Order(
            receiverPhoneNumber = receiverPhoneNumber,
            receiverName = receiverName,
            receiverAddr = receiverAddr,
            receiverAddrDetail = receiverAddrDetail,
            totalPrice = totalPrice,
            finalPrice = finalPrice,
            rsrvDate = rsrvDate,
            status = OrderStatus.ORDER_REQUEST,
            memberId = memberId
        )
    }
}
