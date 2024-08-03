package org.wine.orderservice.order.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import org.wine.orderservice.order.dto.OrderDetailDto
import org.wine.orderservice.order.dto.OrderDto
import org.wine.orderservice.order.dto.request.OrderRequestDto
import org.wine.orderservice.order.entity.Order
import org.wine.orderservice.order.entity.OrderDetail
import org.wine.orderservice.order.entity.OrderStatus
import java.time.Instant

@Mapper(componentModel = "spring", uses = [OrderDetailMapper::class])
interface OrderMapper {
    @Mapping(source = "orderId", target = "orderId")
    fun toOrderDto(order: Order): OrderDto

}
