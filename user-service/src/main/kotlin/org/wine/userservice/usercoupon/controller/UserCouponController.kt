package org.wine.userservice.usercoupon.controller

import org.springframework.web.bind.annotation.*
import org.wine.userservice.usercoupon.service.CouponService


@RestController
@RequestMapping("/api/usercoupon")
class UserCouponController(private val couponService: CouponService) {
    @GetMapping("/v1")
    fun getUserInfo():List<Map<String, Any>>? {
        return couponService.getCoupons()
//        return ApiResponse.Success(data = map)
    }
    @GetMapping("/v1/each")
    fun getUserInfo(@RequestParam("couponId") couponId: Long):List<Map<String, Any>>? {
        return couponService.getCoupon(couponId)
//        return ApiResponse.Success(data = map)
    }
}