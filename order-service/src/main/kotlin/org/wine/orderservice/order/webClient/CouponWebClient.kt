package org.wine.orderservice.order.webClient

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBodyOrNull
import org.wine.orderservice.order.dto.CouponDto
import org.wine.orderservice.order.dto.Response

@Component
class CouponWebClient @Autowired constructor(
    private val webClient: WebClient
){

    @Value("\${uri.coupon-service}")
    lateinit var couponService: String
    suspend fun getCoupon(couponId: String): Response<CouponDto> {
        return webClient.get()
            .uri("$couponService/api/coupon/v1/$couponId")
            .retrieve()
            .awaitBodyOrNull<Response<CouponDto>>() ?: throw Exception("Received null response")
    }
}