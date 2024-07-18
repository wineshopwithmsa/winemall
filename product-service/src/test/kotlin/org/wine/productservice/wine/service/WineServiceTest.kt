package org.wine.productservice.wine.service

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mapstruct.factory.Mappers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.wine.productservice.auth.AuthService
import org.wine.productservice.wine.dto.*
import org.wine.productservice.wine.entity.Category
import org.wine.productservice.wine.entity.Region
import org.wine.productservice.wine.entity.Wine
import org.wine.productservice.wine.mapper.WineMapper
import org.wine.productservice.wine.repository.CategoryRepository
import org.wine.productservice.wine.repository.RegionRepository
import org.wine.productservice.wine.repository.WineCategoryRepository
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
    private lateinit var wineCategoryRepository: WineCategoryRepository

    @Mock
    private var wineMapper: WineMapper = Mappers.getMapper(WineMapper::class.java)

    @Mock
    private lateinit var authService: AuthService

    @InjectMocks
    private lateinit var wineService: WineService

    @Test
    fun `addWine - 유효한 RequestDto가 들어오면 와인을 저장하고 WineDto 반환`() {
        // Given
        val wineRequestDto = WineCreateRequestDto(
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
            categories =  mockCategories.map { category -> CategoryDto.fromCategory(category) }.toSet(),
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

    @Test
    fun `updateWine - 유효한 RequestDto가 들어오면 와인을 수정하고 wineDto 반환`() {
        // Given
        val wineId = 1L
        val mockRegion = Region(1L, "Old Region", Instant.now())
        val existingWine = Wine(
            wineId = wineId,
            name = "Old Name",
            description = "Old Description",
            alcoholPercentage = BigDecimal.TEN,
            region = mockRegion,
            registrantId = 1L
        )
        val updatedRegion = Region(2L, "Updated Region", Instant.now())
        val updatedCategories = setOf(Category(3L, "Updated Category"))
        val requestDto = WineUpdateRequestDto(
            name = "Updated Name",
            description = "Updated Description",
            alcoholPercentage = BigDecimal.valueOf(12.34),
            regionId = 2L,
            categoryIds = setOf(3L)
        )
        val expectedWineDto = WineDto(
            id = wineId,
            name = "Updated Name",
            description = "Updated Description",
            alcoholPercentage = BigDecimal.valueOf(12.34),
            region = RegionDto(2L, "Updated Region"),
            categories = setOf(CategoryDto(3L, "Updated Category")),
        )

        `when`(wineRepository.findById(wineId)).thenReturn(Optional.of(existingWine))
        `when`(regionRepository.findById(2L)).thenReturn(Optional.of(updatedRegion))
        // Adjusted to reflect the behavior of being called twice
        `when`(categoryRepository.findAllById(setOf(3L))).thenReturn(
            updatedCategories.toList(),
            updatedCategories.toList()
        )
        `when`(wineRepository.save(any(Wine::class.java))).thenAnswer { it.arguments[0] }
        `when`(wineMapper.toWineDto(existingWine)).thenReturn(expectedWineDto)

        // When
        val result = wineService.updateWine(wineId, requestDto)

        // Then
        verify(wineRepository).findById(wineId)
        verify(regionRepository).findById(2L)
        // Verify findAllById was called twice
        verify(categoryRepository, times(2)).findAllById(setOf(3L))
        verify(wineCategoryRepository).deleteAll(anyCollection())
        verify(wineRepository).save(any(Wine::class.java))

        assert(result == expectedWineDto)
    }
}