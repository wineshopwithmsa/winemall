package org.wine.productservice.wine.service

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.wine.productservice.wine.dto.WineSaleCreateRequestDto
import org.wine.productservice.wine.dto.WineSaleDto
import org.wine.productservice.wine.dto.WineSalesRequestDto
import org.wine.productservice.wine.entity.SaleStatus
import org.wine.productservice.wine.entity.WineSale
import org.wine.productservice.wine.mapper.WineSaleMapper
import org.wine.productservice.wine.repository.WineSaleRepository
import java.time.Instant
import kotlin.test.assertEquals

@ExtendWith(MockitoExtension::class)
class WineSaleServiceTest {

    @Mock
    private lateinit var authService: AuthService

    @Mock
    private lateinit var wineSaleMapper: WineSaleMapper

    @Mock
    private lateinit var wineSaleRepository: WineSaleRepository

    @InjectMocks
    private lateinit var wineSaleService: WineSaleService


    @Test
    fun `addWineSale - Success`() {
        // Given
        val requestDto = WineSaleCreateRequestDto(
            wineId = 1L,
            price = 1999,
            quantity = 100,
            saleStartTime = Instant.parse("2023-01-01T00:00:00Z"),
            saleEndTime = Instant.parse("2023-12-31T23:59:59Z")
        )
        val userId = 1L
        val wineSale = WineSale(
            wineSaleId = 1L,
            wineId = 1L,
            price = 1999,
            registeredQuantity = 100,
            stockQuantity = 100,
            soldQuantity = 0,
            saleStatus = SaleStatus.SALE_UPCOMING,
            saleStartTime = Instant.parse("2023-01-01T00:00:00Z"),
            saleEndTime = Instant.parse("2023-12-31T23:59:59Z"),
            sellerId = userId
        )
        val expectedWineSaleDto = WineSaleDto.fromSaleWine(wineSale)

        `when`(authService.getAccountId()).thenReturn(userId)
        `when`(wineSaleMapper.toWineSale(requestDto)).thenReturn(wineSale)
        `when`(wineSaleRepository.save(wineSale)).thenReturn(wineSale)
        `when`(wineSaleMapper.toWineSaleDto(wineSale)).thenReturn(expectedWineSaleDto)
        // When
        val result = wineSaleService.addWineSale(requestDto)

        // Then
        assertEquals(expectedWineSaleDto, result)
    }

    @Test
    fun `getWineSales - Success`() {
        // Given
        val requestDto = WineSalesRequestDto(ids = listOf(1L, 2L, 3L))
        val wineSales = listOf(
            WineSale(
                wineSaleId = 1L,
                wineId = 1L,
                price = 1999,
                registeredQuantity = 100,
                stockQuantity = 100,
                soldQuantity = 0,
                saleStatus = SaleStatus.SALE_UPCOMING,
                saleStartTime = Instant.parse("2023-01-01T00:00:00Z"),
                saleEndTime = Instant.parse("2023-12-31T23:59:59Z"),
                sellerId = 1L
            )
        )
        val expectedWineSaleDtos = wineSales.map { WineSaleDto.fromSaleWine(it) }

        `when`(requestDto.ids?.let { wineSaleRepository.findAllByWineSaleIdIn(it) }).thenReturn(wineSales)
        wineSales.forEachIndexed { index, wineSale ->
            `when`(wineSaleMapper.toWineSaleDto(wineSale)).thenReturn(expectedWineSaleDtos[index])
        }

        // When
        val result = wineSaleService.getWineSales(requestDto)

        // Then
        assertEquals(expectedWineSaleDtos, result)
        requestDto.ids?.let { verify(wineSaleRepository).findAllByWineSaleIdIn(it) }
    }
}