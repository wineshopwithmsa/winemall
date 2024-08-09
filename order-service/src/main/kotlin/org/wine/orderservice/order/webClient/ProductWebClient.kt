package org.wine.orderservice.order.webClient

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBodyOrNull
import org.wine.orderservice.order.dto.CouponDto
import org.wine.orderservice.order.dto.Response
import org.wine.orderservice.order.dto.WineSaleDto

@Component
class ProductWebClient @Autowired constructor(
    private val webClient: WebClient
){

    @Value("\${uri.product-service}")
    lateinit var productService: String

    suspend fun getWineSaleInfo(wineSaleIdsParam: String): Response<List<WineSaleDto>> {
        return webClient.get()
            .uri("$productService/api/wine-sales/v1?ids=$wineSaleIdsParam")
            .retrieve()
            .awaitBodyOrNull<Response<List<WineSaleDto>>>() ?: throw Exception("Received null response")
    }
}