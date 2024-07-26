package org.wine.productservice.wine.dto

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.Instant

@JsonInclude(JsonInclude.Include.NON_NULL)
data class WineSaleCreateRequestDto(
    val wineId: Long,
    val price: Int,
    val quantity: Int,
    val saleStartTime: Instant,
    val saleEndTime: Instant,
)
