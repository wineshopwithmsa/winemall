package org.wine.userservice.usercoupon.service

import org.springframework.stereotype.Service
import org.wine.userservice.usercoupon.client.CouponGateway

@Service
class CouponService(private val couponGateway: CouponGateway) {

    fun getCoupons(): List<Map<String, Any>>? {
        val response = couponGateway.getCoupons()
        return response?.get("data") as? List<Map<String, Any>>
    }
    fun getCoupon(couponId:Long): List<Map<String, Any>>? {
        val response = couponGateway.getCoupon(couponId)
        return response?.get("data") as? List<Map<String, Any>>
    }
}