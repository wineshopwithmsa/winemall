package org.wine.orderservice.order

import com.fasterxml.jackson.annotation.JsonProperty
import org.wine.orderservice.order.dto.CategoryDto
import java.math.BigDecimal

data class WineSaleDto(
    val wineSaleId : Long,
    val price: Int
)
