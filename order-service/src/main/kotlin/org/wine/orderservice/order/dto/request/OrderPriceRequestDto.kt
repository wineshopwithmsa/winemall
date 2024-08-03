package org.wine.orderservice.order.dto.request


import jakarta.validation.constraints.Size

data class OrderPriceRequestDto(

    @field:Size(min = 1, message = "At least 1 order required")
    val productList: List<OrderWineRequestDto>,
    val couponId : Long? = null
)
