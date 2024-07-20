package org.wine.productservice.wine.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.wine.productservice.product.controller.WineController
import org.wine.productservice.wine.dto.*
import org.wine.productservice.wine.service.WineService
import java.math.BigDecimal
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.wine.productservice.shared.pagination.PaginationLinks
import org.wine.productservice.shared.pagination.PaginationMetadata
import org.wine.productservice.wine.exception.WineNotFoundException

@WebMvcTest(WineController::class)
class WineControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var wineService: WineService


    @Test
    fun `addWine - Created`() {
        // Given
        val requestDto = WineCreateRequestDto(
            name = "Chardonnay",
            description = "A popular white wine with a wide range of flavors from apple to tropical fruits.",
            regionId = 1L,
            alcoholPercentage = BigDecimal("13.5"),
            categoryIds = setOf(3L, 4L)
        )
        val expectedWineDto = WineDto(
            id = 1L,
            name = "Chardonnay",
            description = "A popular white wine with a wide range of flavors from apple to tropical fruits.",
            region = RegionDto(id = 1L, name = "Napa Valley"),
            categories = setOf(CategoryDto(id = 1L, name = "White")),
            alcoholPercentage = BigDecimal("13.5"),
        )
        val expectedResponse = WineResponse(expectedWineDto)
        `when`(wineService.addWine(requestDto)).thenReturn(expectedWineDto)

        // When & Then
        mockMvc.perform(post("/api/wines/v1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestDto)))
            .andExpect(status().isCreated)
            .andExpect(content().json(objectMapper.writeValueAsString(
                ApiResponse.Success(
                    status = 201,
                    message = "Created",
                    data = expectedResponse
                )
            )))
    }

    @Test
    fun `getWine - Success`() {
        // Given
        val wineId = 1L
        val expectedWine = WineDto(
            id = wineId,
            name = "와인이름",
            description = "와인 설명",
            alcoholPercentage = BigDecimal("12.5"),
            region = RegionDto(id = 1L, name = "region1"),
            categories = setOf(CategoryDto(id = 1L, name = "카테고리이름"))
        )
        `when`(wineService.getWine(wineId)).thenReturn(expectedWine)

        // When & Then
        mockMvc.perform(get("/api/wines/v1/$wineId"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(200))
            .andExpect(jsonPath("$.message").value("Success"))
            .andExpect(jsonPath("$.data.wine.id").value(wineId))
            .andExpect(jsonPath("$.data.wine.name").value("와인이름"))
            .andExpect(jsonPath("$.data.wine.description").value("와인 설명"))
            .andExpect(jsonPath("$.data.wine.alcoholPercentage").value(12.5))
            .andExpect(jsonPath("$.data.wine.region.id").value(1))
            .andExpect(jsonPath("$.data.wine.region.name").value("region1"))
            .andExpect(jsonPath("$.data.wine.categories[0].id").value(1))
            .andExpect(jsonPath("$.data.wine.categories[0].name").value("카테고리이름"))
    }

    @Test
    fun `getWine - Not Found`() {
        // Given
        val wineId = 999L
        `when`(wineService.getWine(wineId)).thenThrow(WineNotFoundException("Wine not found with id: $wineId"))

        // When & Then
        mockMvc.perform(get("/api/wines/v1/$wineId"))
            .andExpect(status().isNotFound)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(404))
            .andExpect(jsonPath("$.message").value("Wine not found with id: $wineId"))
    }

    @Test
    fun `getWines - Success with default parameters`() {
        // Given
        val expectedResponse = createMockPaginatedResponse(1, 10)
        `when`(wineService.getWines(page = 1, perPage = 10, regionIds = null, categoryIds = null))
            .thenReturn(expectedResponse)

        // When & Then
        mockMvc.perform(get("/api/wines/v1"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(200))
            .andExpect(jsonPath("$.message").value("Success"))
            .andExpect(jsonPath("$.data.wines.metadata.page").value(1))
            .andExpect(jsonPath("$.data.wines.metadata.perPage").value(10))
            .andExpect(jsonPath("$.data.wines.metadata.totalPages").value(10))
            .andExpect(jsonPath("$.data.wines.metadata.totalCount").value(30))
            .andExpect(jsonPath("$.data.wines.wines[0].id").value(1))
            .andExpect(jsonPath("$.data.wines.wines[0].name").value("와인이름"))
            .andExpect(jsonPath("$.data.wines.links.self").value("api/v1/wines?page=1&perPage=10"))
    }

    @Test
    fun `getWines - Success with custom parameters`() {
        // Given
        val expectedResponse = createMockPaginatedResponse(2, 5)
        `when`(wineService.getWines(page = 2, perPage = 5, regionIds = listOf(1L, 2L), categoryIds = listOf(3L)))
            .thenReturn(expectedResponse)

        // When & Then
        mockMvc.perform(get("/api/wines/v1")
            .param("page", "2")
            .param("perPage", "5")
            .param("regionId", "1,2")
            .param("categoryId", "3"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.data.wines.metadata.page").value(2))
            .andExpect(jsonPath("$.data.wines.metadata.perPage").value(5))
            .andExpect(jsonPath("$.data.wines.links.self").value("api/v1/wines?page=2&perPage=5"))
    }

    @Test
    fun `getWines - Bad Request with invalid page`() {
        mockMvc.perform(get("/api/wines/v1").param("page", "0"))
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `getWines - Bad Request with invalid perPage`() {
        mockMvc.perform(get("/api/wines/v1").param("perPage", "101"))
            .andExpect(status().isBadRequest)
    }

    private fun createMockPaginatedResponse(page: Int, perPage: Int): PaginatedWineResponseDto {
        val wine = WineDto(
            id = 1L,
            name = "와인이름",
            description = "와인 설명",
            alcoholPercentage = BigDecimal("12.5"),
            region = RegionDto(id = 1L, name = "region1"),
            categories = setOf(CategoryDto(id = 1L, name = "카테고리이름"))
        )

        return PaginatedWineResponseDto(
            metadata = PaginationMetadata(
                page = page,
                perPage = perPage,
                totalPages = 10,
                totalCount = 30
            ),
            wines = listOf(wine),
            links = PaginationLinks(
                self = "api/v1/wines?page=$page&perPage=$perPage",
                first = "api/v1/wines?page=1&perPage=$perPage",
                next = "api/v1/wines?page=${page + 1}&perPage=$perPage",
                prev = "api/v1/wines?page=${if (page > 1) page - 1 else 1}&perPage=$perPage",
                last = "api/v1/wines?page=10&perPage=$perPage"
            )
        )
    }
}