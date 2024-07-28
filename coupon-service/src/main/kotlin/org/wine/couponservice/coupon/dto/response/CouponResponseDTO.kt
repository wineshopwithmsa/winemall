package org.wine.couponservice.coupon.dto.response

import org.wine.couponservice.coupon.entity.Coupon
import java.time.LocalDate
import java.time.LocalDateTime


data class CouponResponseDTO(
    val couponId: Long,
    val code: String,
    val description: String,
    val discountType: Coupon.DiscountType,
    val discountValue: Double,
    val expiryDate: LocalDateTime,
)