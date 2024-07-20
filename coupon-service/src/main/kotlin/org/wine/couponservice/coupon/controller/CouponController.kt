package org.wine.couponservice.coupon.controller
import ApiResponse
import org.springframework.web.bind.annotation.*
import org.wine.couponservice.coupon.dto.request.CouponRequestDTO
import org.wine.couponservice.coupon.service.CouponService

@RestController
@RequestMapping("/api/coupon")
class CouponController(private val couponService: CouponService) {

    @PostMapping("/v1")
    fun createCoupon(@RequestBody couponRequestDTO: CouponRequestDTO): ApiResponse<Any>  {
        val createdCoupon = couponService.createCoupon(couponRequestDTO)
        return ApiResponse.Success(data=createdCoupon)
    }

    @GetMapping("/v1/{id}")
    fun getCouponById(@PathVariable id: Long): ApiResponse<Any> {
        val coupon = couponService.getCouponById(id)
        return ApiResponse.Success(data=coupon)
    }

    @GetMapping("/v1")
    fun getAllCoupons(): ApiResponse<Any> {
        val coupons = couponService.getAllCoupons()
        return ApiResponse.Success(data = coupons)
    }
}