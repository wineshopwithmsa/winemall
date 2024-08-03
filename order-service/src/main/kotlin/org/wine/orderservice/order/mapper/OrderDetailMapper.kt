package org.wine.orderservice.order.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.wine.orderservice.order.dto.OrderDetailDto
import org.wine.orderservice.order.dto.OrderDto
import org.wine.orderservice.order.dto.request.OrderRequestDto
import org.wine.orderservice.order.dto.request.OrderWineRequestDto
import org.wine.orderservice.order.entity.Order
import org.wine.orderservice.order.entity.OrderDetail

@Mapper(componentModel = "spring")
interface OrderDetailMapper {
    @Mapping(source = "orderDetailId", target = "orderDetailId")
    fun toOrderDetailDto(orderDetail: OrderDetail): OrderDetailDto

    fun toOrderDetail(orderWineRequestDto: OrderWineRequestDto, order: Order): OrderDetail {
        val orderDetail = OrderDetail(
            wineSaleId = orderWineRequestDto.wineSaleId,
            quantity =  orderWineRequestDto.quantity,
            originPrice = orderWineRequestDto.originPrice,
            salePrice = orderWineRequestDto.salePrice,
            order = order
        )

        return orderDetail
    }
}
