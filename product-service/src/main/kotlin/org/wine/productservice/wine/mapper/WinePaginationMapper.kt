package org.wine.productservice.wine.mapper

import org.springframework.data.domain.Page
import org.springframework.stereotype.Component
import org.wine.productservice.shared.pagination.PaginationMapper.Companion.createPaginationLinks
import org.wine.productservice.shared.pagination.PaginationMapper.Companion.toPaginationMetadata
import org.wine.productservice.wine.dto.PaginatedWineResponseDto
import org.wine.productservice.wine.dto.WineDto
import org.wine.productservice.wine.entity.Wine

@Component
class WinePaginationMapper {
    fun toPaginatedWineResponse(winesPage: Page<Wine>, baseUrl: String): PaginatedWineResponseDto {
        val wines = winesPage.content.map { WineDto.fromWine(it) }
        val metadata = toPaginationMetadata(winesPage)
        val links = createPaginationLinks(winesPage, baseUrl)

        return PaginatedWineResponseDto(metadata, wines, links)
    }
}
