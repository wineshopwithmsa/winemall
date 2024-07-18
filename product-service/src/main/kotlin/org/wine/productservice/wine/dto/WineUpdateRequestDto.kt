package org.wine.productservice.wine.dto

import com.fasterxml.jackson.annotation.JsonInclude
import jakarta.validation.constraints.*
import java.io.Serializable
import java.math.BigDecimal

@JsonInclude(JsonInclude.Include.NON_NULL)
data class WineUpdateRequestDto(
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters long")
    val name: String?,

    @Size(max = 5000, message = "Description must be at most 500 characters long")
    val description: String?,

    @Positive(message = "RegionId must be a positive number")
    val regionId: Long?,

    @DecimalMin(value = "0.0", inclusive = false, message = "Alcohol percentage must be greater than 0")
    @DecimalMax(value = "100.0", inclusive = true, message = "Alcohol percentage must be less than or equal to 100")
    val alcoholPercentage: BigDecimal?,

    @Size(max = 5, message = "At most 5 categories can be tagged")
    val categoryIds: Set<Long>?
) : Serializable
