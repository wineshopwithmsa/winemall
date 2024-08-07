package org.wine.userservice.membercoupon.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "coupon-gateway", url = "http://localhost:9999")
interface CouponGateway {

    @GetMapping(
        value = ["/api/coupon/v1"],
        consumes = ["application/json"],
//        headers = ["requester-name=my-api-request"]
    )
    suspend fun getCoupons(): Map<String, Object>?
    @GetMapping("/api/coupon/v1/{couponId}")
    fun getCoupon(@PathVariable couponId: Long): Map<String, Object>?
}