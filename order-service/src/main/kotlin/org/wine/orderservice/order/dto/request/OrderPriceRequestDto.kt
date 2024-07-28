package org.wine.orderservice.order.dto.request


import jakarta.validation.constraints.Size
import org.wine.orderservice.order.dto.OrderDto

data class OrderPriceRequestDto(

    @field:Size(min = 1, message = "At most 1 order required")
    val productList: List<OrderDto>,
    val couponId : Long? = null
)
