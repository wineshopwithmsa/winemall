package org.wine.couponservice.coupon.controller
import ApiResponse
import org.springframework.web.bind.annotation.*
import org.wine.couponservice.coupon.dto.request.CouponRequestDTO
import org.wine.couponservice.coupon.service.CouponFacade
import org.wine.couponservice.coupon.service.CouponService

@RestController
@RequestMapping("/api/coupon")
class CouponController(
    private val couponService: CouponService,
    private val couponFacade: CouponFacade
) {

    @PostMapping("/v1")
    fun createCoupon(@RequestBody couponRequestDTO: CouponRequestDTO): ApiResponse<Any>  {
        val createdCoupon = couponService.createCoupon(couponRequestDTO)
        return ApiResponse.Success(data=createdCoupon)
    }
    @PostMapping("/v1/limit/{nums}")
    fun createCouponLimit(@PathVariable nums:Int): ApiResponse<Any>  {
        val createdCoupon = couponService.createCouponLimit(nums)
        return ApiResponse.Success(data=createdCoupon)
    }

    @GetMapping("/v1/{id}")
    fun getCouponById(@PathVariable id: Long): ApiResponse<Any> {
        val coupon = couponService.getCouponById(id)
        return ApiResponse.Success(data=coupon)
    }
    @PutMapping("/v1/{code}")
    fun updateCouponById(@PathVariable code: String): ApiResponse<Any> {
        val coupon = couponService.updateCouponByCode(code)
        return ApiResponse.Success(data=coupon)
    }

    @GetMapping("/v1")
    fun getAllCoupons(): ApiResponse<Any> {
        val coupons = couponService.getAllCoupons()
        return ApiResponse.Success(data = coupons)
    }

//    @PostMapping("/v1/fcfs/{cId}")
//    fun createFcfsCoupon(@PathVariable cid:String): ApiResponse<Any>{
//        val coupon = couponService.createFcfsCoupon(cid)
//        return ApiResponse.Success(data = "")
//    }
    @PostMapping("/v1/fcfs/{nums}")
    fun createFcfsCoupons(@PathVariable nums:Long): ApiResponse<Any>{
        val coupons = couponFacade.asyncCreateCoupons(nums);
        return ApiResponse.Success(data = "sucess")
    }
}