package org.wine.couponservice.coupon.dto.request

import org.wine.couponservice.coupon.entity.DiscountType
import java.time.LocalDate
import java.time.LocalDateTime


data class CouponRequestDTO(
    val code: String,
    val description: String,
    val discountType: DiscountType,
    val discountValue: Double,
    val expiryDate: LocalDateTime
)