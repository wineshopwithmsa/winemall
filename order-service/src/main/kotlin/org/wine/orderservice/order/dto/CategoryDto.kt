package org.wine.orderservice.order.dto

import io.swagger.v3.oas.annotations.media.Schema

data class CategoryDto(
    val id: Long,
    val name: String
)
