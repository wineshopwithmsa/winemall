package org.wine.orderservice.order.dto.request


import jakarta.persistence.Column
import jakarta.validation.constraints.NotNull
import org.wine.orderservice.order.entity.Order
import org.wine.orderservice.order.entity.OrderDetail
import org.wine.orderservice.order.entity.OrderStatus


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

    val wineList : List<OrderWineRequestDto>,

    val rsrvDate: String? = null,

    val couponId : Long? = null
){
    fun toOrder(memberId: Long, orderRequestDto: OrderRequestDto) : Order {
        return Order(
            memberId = memberId,
            receiverPhoneNumber = orderRequestDto.receiverPhoneNumber,
            receiverName = orderRequestDto.receiverName,
            receiverAddr = orderRequestDto.receiverAddr,
            receiverAddrDetail = orderRequestDto.receiverAddrDetail,
            totalPrice = orderRequestDto.totalPrice,
            finalPrice = orderRequestDto.finalPrice,
            rsrvDate = orderRequestDto.rsrvDate,
            status = OrderStatus.ORDER_REQUEST,
        )
    }
}
