package org.wine.userservice.usercoupon.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import java.util.*

@FeignClient(name = "coupon-gateway", url = "http://localhost:9999")
interface CouponGateway {

    @GetMapping(
        value = ["/api/coupon/v1"],
        consumes = ["application/json"],
//        headers = ["requester-name=my-api-request"]
    )
    fun getCoupons(): Map<String, Object>?
    @GetMapping(
        value = ["/api/coupon/v1"],
        consumes = ["application/json"],
//        headers = ["requester-name=my-api-request"]
    )
    fun getCoupon(@RequestParam("couponId") couponId: Long): Map<String, Any>?
}