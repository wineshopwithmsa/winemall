package org.wine.orderservice.order.feign

import io.swagger.annotations.ApiResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "couponSercieFeign", url = "\${feign.coupon-service-feign.url}")
interface CouponServiceFeign {
    @GetMapping("/api/coupon/v1/{id}")
    fun getCouponInfo(@PathVariable(name = "id") id: String) :ApiResponse

}