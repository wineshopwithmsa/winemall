package org.wine.orderservice.order.service

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.wine.orderservice.order.dto.OrderDto
import org.wine.orderservice.order.dto.request.OrderPriceRequestDto

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")  // Assuming you have a test profile configured
class OrderServiceIntegrationTest {

    @Autowired
    private lateinit var orderService: OrderService

    @Test
    @Disabled("Manual test - not for automated runs")
    fun `Execute calculatePrice(product-service가 실행된다는 전제로 테스트)`() = runBlocking {
        // Given
        val orderPriceRequestDto = OrderPriceRequestDto(
            productList = listOf(
                OrderDto(wineSaleId = 1L, quantity = 2),
                OrderDto(wineSaleId = 2L, quantity = 3)
            ),
            couponId = 123L
        )

        // When
        try {
            orderService.calculatePrice(orderPriceRequestDto)
            // If we reach here without exception, consider it a success
            println("calculatePrice executed successfully")
        } catch (e: Exception) {
            // Log the exception and fail the test
            println("Exception occurred: ${e.message}")
            throw e  // This will cause the test to fail
        }
    }
}
