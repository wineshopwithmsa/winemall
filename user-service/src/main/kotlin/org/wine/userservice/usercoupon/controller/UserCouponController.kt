package org.wine.userservice.usercoupon.controller

import ApiResponse
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.*
import org.wine.userservice.usercoupon.gateway.CouponService


@RestController
@RequestMapping("/api/usercoupon")
class UserCouponController(private val couponService: CouponService) {
    @GetMapping("/v1")
    fun getUserInfo():List<Map<String, Any>>? {
        return couponService.getOrders()
//        return ApiResponse.Success(data = map)
    }
}