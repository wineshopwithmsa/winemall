package org.wine.productservice.wine.mapper

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.wine.productservice.wine.testutil.TestData

@ExtendWith(SpringExtension::class)
@SpringBootTest
class RegionMapperTest {

    @Autowired
    private lateinit var regionMapper: RegionMapper

    @Test
    fun `Region to RegionDto`() {
        // Given
        val region = TestData.createRegion()

        // When
        val regionDto = regionMapper.toRegionDto(region)

        // Then
        assertEquals(region.regionId, regionDto.id)
        assertEquals(region.name, regionDto.name)
    }
}