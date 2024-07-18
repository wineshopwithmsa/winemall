package org.wine.userservice.usercoupon.gateway

import org.springframework.stereotype.Service
import org.wine.userservice.usercoupon.gateway.CouponGateway
import java.util.*

@Service
class CouponService(private val couponGateway: CouponGateway) {

    fun getOrders(): List<Map<String, Any>>? {
        val response = couponGateway.getOrders()
        return response?.get("data") as? List<Map<String, Any>>
    }
}