package org.wine.productservice.wine.dto

import com.fasterxml.jackson.annotation.JsonInclude
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.*
import java.io.Serializable
import java.math.BigDecimal

@JsonInclude(JsonInclude.Include.NON_NULL)
data class WineUpdateRequestDto(
    @field:Schema(description = "와인 이름", example = "wine2")
    @field:Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters long")
    val name: String?,

    @field:Schema(description = "와인 설명", example = "와인 설명 수정")
    @field:Size(max = 5000, message = "Description must be at most 500 characters long")
    val description: String?,

    @field:Schema(description = "지역 ID", example = "1")
    @field:Positive(message = "RegionId must be a positive number")
    val regionId: Long?,

    @field:Schema(description = "알콜 도수(0.0 < x <= 100.0)", example = "15.0")
    @field:DecimalMin(value = "0.0", inclusive = false, message = "Alcohol percentage must be greater than 0")
    @field:DecimalMax(value = "100.0", inclusive = true, message = "Alcohol percentage must be less than or equal to 100")
    val alcoholPercentage: BigDecimal?,

    @field:Schema(description = "카테고리 ID 목록", example = "[2]")
    @field:Size(max = 5, message = "At most 5 categories can be tagged")
    val categoryIds: Set<Long>?
) : Serializable
