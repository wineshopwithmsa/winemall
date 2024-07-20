package org.wine.orderservice.order.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class OrderPriceRequestDto(
    @get:NotEmpty
    val productIdList: List<@NotNull Long>,

    val couponIdList : List<@NotNull Long>
)
