package org.wine.userservice.membercoupon.dto.response

import org.wine.userservice.membercoupon.entity.MemberCoupon
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
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
        fun mapToMemberCouponDtoEach(memberCoupon: MemberCoupon): Mono<MemberCouponResponseDto> {
            return Mono.fromCallable { MemberCouponResponseDto(
                memberCouponId = memberCoupon.memberCouponId,
                couponId = memberCoupon.couponId,
                isUsed = memberCoupon.isUsed,
                issuedTime = memberCoupon.issuedTime,
                expireTime = memberCoupon.expireTime,
                usedTime = memberCoupon.usedTime
            ) }
        }

        fun mapToMemberCouponDto(memberCoupons: List<MemberCoupon>): Flux<MemberCouponResponseDto> {
            return Flux.fromIterable(memberCoupons)
                .flatMap { mapToMemberCouponDtoEach(it) }
//            return memberCoupons.map { mapToMemberCouponDtoEach(it) }
        }
    }

}