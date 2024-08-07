package org.wine.userservice.membercoupon.controller

import ApiResponse
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.*
import org.wine.userservice.common.config.MemberCouponApiSpec
import org.wine.userservice.membercoupon.dto.request.MemberCouponApplyRequestDto
import org.wine.userservice.membercoupon.service.MemberCouponService


@RestController
@RequestMapping("/api/membercoupon")
//@Tag(name = "MemberCoupon", description = "유저 쿠폰 관련 API")
class MemberCouponController(private val memberCouponService: MemberCouponService) {

    @GetMapping("/v1/coupons")
    @MemberCouponApiSpec.GetMemberCoupons
    suspend fun getUserInfo():ApiResponse<Any> {
        return ApiResponse.Success(data = memberCouponService.getCoupons())
    }
    @GetMapping("/v1/each")
    suspend fun getUserInfo(@RequestParam("couponId") couponId: Long):ApiResponse<Any> {

        return ApiResponse.Success(data = memberCouponService.getCoupon(couponId))
    }
    @PostMapping("/v1/member/apply")
    suspend fun applyCouponMember(@RequestBody memberCouponApplyRequestDto: MemberCouponApplyRequestDto):ApiResponse<Any>{
        val appliedCoupon = memberCouponService.applyCouponMember(memberCouponApplyRequestDto)
        return ApiResponse.Success(message = "success", data = appliedCoupon)
    }
    @GetMapping("/v1/member/coupons")
    suspend fun getCouponMember(@RequestHeader headers: HttpHeaders):ApiResponse<Any>{
        val appliedMemberCoupons = memberCouponService.getCouponMember(headers)
        return ApiResponse.Success(message = "success", data = appliedMemberCoupons)
    }

    @PatchMapping("/v1/member/coupon/use")
    suspend fun useCouponMember(@RequestParam("memberCouponId") memberCouponId:Long):ApiResponse<Any>{
//        memberCouponService.applyCouponMember(memberCouponApplyRequestDto)
        val updatedCoupon = memberCouponService.useCouponMember(memberCouponId)
        return ApiResponse.Success(message = "success",data=updatedCoupon)
    }
}