package org.wine.productservice.wine.dto

import com.fasterxml.jackson.annotation.JsonInclude
import io.swagger.v3.oas.annotations.media.Schema
import java.time.Instant
import jakarta.validation.constraints.*


@JsonInclude(JsonInclude.Include.NON_NULL)
data class WineSaleCreateRequestDto(
    val wineId: Long,
    val price: Int,
    val quantity: Int,
    val saleStartTime: Instant,
    val saleEndTime: Instant,
)
