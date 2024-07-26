package org.wine.productservice.wine.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.wine.productservice.wine.dto.WineSaleCreateRequestDto
import org.wine.productservice.wine.dto.WineSaleDto
import org.wine.productservice.wine.dto.WineSaleResponse
import org.wine.productservice.wine.entity.SaleStatus
import org.wine.productservice.wine.service.WineSaleService
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import java.time.Instant
import kotlin.test.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.wine.productservice.product.controller.WineSaleController


@WebMvcTest(WineSaleController::class)
class WineSaleControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var wineSaleService: WineSaleService

    @Test
    fun `createWine - Created`() {
        // Given
        val requestDto = WineSaleCreateRequestDto(
            wineId = 1L,
            price = 1999,
            quantity = 100,
            saleStartTime = Instant.parse("2023-01-01T00:00:00Z"),
            saleEndTime = Instant.parse("2023-12-31T23:59:59Z")
        )
        val expectedWineSaleDto = WineSaleDto(
            id = 1L,
            sellerId = 1L,
            saleStatus = SaleStatus.SALE_UPCOMING,
            wineId = 1L,
            price = 1999,
            registeredQuantity = 100,
            stockQuantity = 100,
            soldQuantity = 0,
            saleStartTime = Instant.parse("2023-01-01T00:00:00Z"),
            saleEndTime = Instant.parse("2023-12-31T23:59:59Z"),
        )
        val expectedResponse = WineSaleResponse(expectedWineSaleDto)
        `when`(wineSaleService.addWineSale(requestDto)).thenReturn(expectedWineSaleDto)

        // When & Then
        mockMvc.perform(post("/api/wine-sales/v1")
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
}