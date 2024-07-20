package org.wine.couponservice.coupon.dto.response

import org.wine.couponservice.coupon.entity.DiscountType
import java.time.LocalDate

data class CouponResponseDTO(
    val couponId: Long,
    val code: String,
    val description: String,
    val discountType: DiscountType,
    val discountValue: Double,
    val expiryDate: LocalDate
)