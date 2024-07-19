package org.wine.productservice.shared.pagination

import org.springframework.data.domain.Page

class PaginationMapper {
    companion object {
        fun toPaginationMetadata(page: Page<*>): PaginationMetadata {
            return PaginationMetadata(
                page = page.number + 1,
                perPage = page.size,
                totalPages = page.totalPages,
                totalCount = page.totalElements
            )
        }

        fun createPaginationLinks(page: Page<*>, baseUrl: String): PaginationLinks {
            val lastPageNumber = page.totalPages
            return PaginationLinks(
                self = "$baseUrl?page=${page.number + 1}&perPage=${page.size}",
                first = "$baseUrl?page=1&perPage=${page.size}",
                next = if (page.hasNext()) "$baseUrl?page=${page.number + 2}&perPage=${page.size}" else null,
                prev = if (page.hasPrevious()) "$baseUrl?page=${page.number}&perPage=${page.size}" else null,
                last = "$baseUrl?page=$lastPageNumber&perPage=${page.size}"
            )
        }
    }
}

