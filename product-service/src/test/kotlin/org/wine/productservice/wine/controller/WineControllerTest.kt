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
import org.wine.productservice.wine.dto.CategoryDto
import org.wine.productservice.wine.dto.RegionDto
import org.wine.productservice.wine.dto.WineDto
import org.wine.productservice.wine.dto.WineRequestDto
import org.wine.productservice.wine.service.WineService
import java.math.BigDecimal

@WebMvcTest(WineController::class)
class WineControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var wineService: WineService


    @Test
    fun `addWine - Success`() {
        // Given
        val requestDto = WineRequestDto(
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
            category = setOf(CategoryDto(id = 1L, name = "White")),
            alcoholPercentage = BigDecimal("13.5"),
        )
        `when`(wineService.addWine(requestDto)).thenReturn(expectedWineDto)

        // When & Then
        mockMvc.perform(post("/api/wines/v1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestDto)))
            .andExpect(status().isCreated)
            .andExpect(content().json(objectMapper.writeValueAsString(ApiResponse.Success(status = 201, message = "Created", data = mapOf("wine" to expectedWineDto)))))
    }
}