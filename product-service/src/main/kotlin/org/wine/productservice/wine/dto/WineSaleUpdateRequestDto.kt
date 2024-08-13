package org.wine.productservice.wine.dto

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.Instant

@JsonInclude(JsonInclude.Include.NON_NULL)
data class WineSaleUpdateRequestDto(
    val price: Int? = null,
    val quantity: Int? = null,
    val saleStartTime: Instant? = null,
    val saleEndTime: Instant? = null,
)