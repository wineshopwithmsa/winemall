package org.wine.productservice.wine.service

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.boot.test.mock.mockito.MockBean
import org.wine.productservice.auth.AuthService
import org.wine.productservice.wine.dto.CategoryDto
import org.wine.productservice.wine.dto.RegionDto
import org.wine.productservice.wine.dto.WineDto
import org.wine.productservice.wine.dto.WineRequestDto
import org.wine.productservice.wine.entity.Category
import org.wine.productservice.wine.entity.Region
import org.wine.productservice.wine.entity.Wine
import org.wine.productservice.wine.mapper.WineMapper
import org.wine.productservice.wine.repository.CategoryRepository
import org.wine.productservice.wine.repository.RegionRepository
import org.wine.productservice.wine.repository.WineRepository
import java.math.BigDecimal
import java.time.Instant
import java.util.*

@ExtendWith(MockitoExtension::class)
class WineServiceTest {

    @Mock
    private lateinit var wineRepository: WineRepository

    @Mock
    private lateinit var regionRepository: RegionRepository

    @Mock
    private lateinit var categoryRepository: CategoryRepository

    @Mock
    private lateinit var wineMapper: WineMapper

    @Mock
    private lateinit var authService: AuthService

    @InjectMocks
    private lateinit var wineService: WineService

    @Test
    fun `addWine - 유효한 RequestDto가 들어오면 와인을 저장하고 WineDto 반환`() {
        // Given
        val wineRequestDto = WineRequestDto(
            name="Test Wine",
            description = "Description",
            regionId = 1L,
            alcoholPercentage = BigDecimal("12.34"),
            categoryIds = setOf(1L, 2L),
        )
        val mockRegion = Region(
            regionId = 1L,
            name = "Test Region",
            Instant.now())
        val mockCategories = setOf(
            Category(categoryId = 1L, name = "Red",),
            Category(categoryId = 2L, name = "White"),
        )
        val mockWine = Wine(
            1L,
            name = "Test Wine",
            description = "Description",
            alcoholPercentage = BigDecimal("12.34"),
            region = mockRegion,
            registrantId = 1L,
        )
        mockWine.tagWithCategories(mockCategories)
        val expectedWineDto = WineDto(
            id = 1L,
            name = "Test Wine",
            description = "Description",
            BigDecimal("12.34"),
            region = RegionDto(1L, "Test Region"),
            category =  mockCategories.map {category -> CategoryDto.fromCategory(category) }.toSet(),
        )

        val nonNullCategoryIds = wineRequestDto.categoryIds ?: emptySet<Long>()
        `when`(regionRepository.findById(1L)).thenReturn(Optional.of(mockRegion))
        `when`(categoryRepository.findAllById(nonNullCategoryIds)).thenReturn(mockCategories.toList())
        `when`(wineMapper.toWine(wineRequestDto, mockRegion)).thenReturn(mockWine)
        `when`(wineRepository.save(mockWine)).thenReturn(mockWine)
        `when`(wineMapper.toWineDto(mockWine)).thenReturn(expectedWineDto)
        `when`(authService.getAccountId()).thenReturn(1L)

        // When
        val result = wineService.addWine(wineRequestDto)

        // Then
        verify(wineRepository).save(mockWine)
        verify(wineMapper).toWineDto(mockWine)
        assert(result == expectedWineDto)
    }
}