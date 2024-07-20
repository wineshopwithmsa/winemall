package org.wine.productservice.wine.dto

import com.fasterxml.jackson.annotation.JsonInclude
import io.swagger.v3.oas.annotations.media.Schema
import org.wine.productservice.shared.pagination.PaginationLinks
import org.wine.productservice.shared.pagination.PaginationMetadata

@JsonInclude(JsonInclude.Include.NON_NULL)
data class PaginatedWineResponseDto(
    @field:Schema(description = "와인 목록")
    val metadata: PaginationMetadata,
    @field:Schema(description = "와인 목록")
    val wines: List<WineDto>,
    @field:Schema(description = "페이징 링크")
    val links: PaginationLinks
)

