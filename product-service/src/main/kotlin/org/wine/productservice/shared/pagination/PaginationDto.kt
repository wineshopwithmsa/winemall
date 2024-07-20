package org.wine.productservice.shared.pagination

import com.fasterxml.jackson.annotation.JsonInclude
import io.swagger.v3.oas.annotations.media.Schema

@JsonInclude(JsonInclude.Include.NON_NULL)
data class PaginationMetadata(
    @field:Schema(description = "현재 페이지", example = "1")
    val page: Int,
    @field:Schema(description = "페이지 당 아이템 수", example = "10")
    val perPage: Int,
    @field:Schema(description = "전체 페이지 수", example = "10")
    val totalPages: Int,
    @field:Schema(description = "전체 아이템 수", example = "100")
    val totalCount: Long
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class PaginationLinks(
    @field:Schema(description = "현재 페이지 링크", example = "api/v1/resource?page=1&perPage=10")
    val self: String,
    @field:Schema(description = "첫 페이지 링크", example = "api/v1/resource?page=1&perPage=10")
    val first: String,
    @field:Schema(description = "다음 페이지 링크", example = "api/v1/resource?page=2&perPage=10")
    val next: String?,
    @field:Schema(description = "이전 페이지 링크", example = "api/v1/resource?page=1&perPage=10")
    val prev: String?,
    @field:Schema(description = "마지막 페이지 링크", example = "api/v1/resource?page=10&perPage=10")
    val last: String
)