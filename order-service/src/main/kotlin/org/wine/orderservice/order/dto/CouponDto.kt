package org.wine.orderservice.order.dto

import java.time.LocalDate

data class CouponDto(
    val couponId: Long,
    val code: String,
    val description: String,
    val discountType: DiscountType,
    val discountValue: Double,
    val expiryDate: LocalDate
)