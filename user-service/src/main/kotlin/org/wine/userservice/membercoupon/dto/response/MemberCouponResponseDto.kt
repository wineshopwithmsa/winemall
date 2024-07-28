package org.wine.userservice.membercoupon.dto.response

import org.wine.userservice.membercoupon.entity.MemberCoupon
import java.io.Serializable
import java.time.LocalDateTime

/**
 * DTO for {@link org.wine.userservice.membercoupon.entity.MemberCoupon}
 */
data class MemberCouponResponseDto(
    val memberCouponId: Long = 0L,
    val couponId: Long? = null,
    val isUsed: Boolean? = null,
    val issuedTime: LocalDateTime? = null,
    val expireTime: LocalDateTime? = null,
    val usedTime: LocalDateTime = LocalDateTime.parse("9999-12-31T00:00:00")
) : Serializable{
    companion object {
        fun mapToMemberCouponDto(memberCoupon: MemberCoupon): MemberCouponResponseDto {
            return MemberCouponResponseDto(
                memberCouponId = memberCoupon.memberCouponId,
                couponId = memberCoupon.couponId,
                isUsed = memberCoupon.isUsed,
                issuedTime = memberCoupon.issuedTime,
                expireTime = memberCoupon.expireTime,
                usedTime = memberCoupon.usedTime
            )
        }

        fun mapToMemberCouponDto(memberCoupons: List<MemberCoupon>): List<MemberCouponResponseDto> {
            return memberCoupons.map { mapToMemberCouponDto(it) }
        }
    }

}