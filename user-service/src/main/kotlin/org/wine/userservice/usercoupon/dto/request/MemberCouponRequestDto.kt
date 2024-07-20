package org.wine.userservice.usercoupon.dto.request

import java.time.LocalDateTime

data class MemberCouponRequestDto(
    val couponId: Long,
    val isUsed: Boolean,
    val issuedTime: LocalDateTime,
    val expireTime: LocalDateTime
)