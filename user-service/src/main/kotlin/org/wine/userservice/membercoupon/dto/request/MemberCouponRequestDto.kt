package org.wine.userservice.membercoupon.dto.request

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class MemberCouponRequestDto(
    val couponId: Long,
    val isUsed: Boolean,
    val issuedTime: LocalDateTime,
    val expireTime: LocalDateTime
)
fun getCoupon(memberId: Long): MemberCouponRequestDto {
    // 쿠폰 정보를 가져오는 로직을 구현합니다.
    // 예시로 임의의 데이터를 반환합니다.
    return MemberCouponRequestDto(
        couponId = 123L,
        isUsed = false,
        issuedTime = LocalDateTime.now().minusDays(1),
        expireTime = LocalDateTime.now().plusDays(30)
    )
}
fun mapToMemberCouponRequestDto(map: Map<String, Any>?): MemberCouponRequestDto {
    if (map == null) {
        throw IllegalArgumentException("Map cannot be null")
    }
    println(map.toString())

    val couponId = (map.get("couponId") as? Number)?.toLong() ?: throw IllegalArgumentException("couponId is required")
    val isUsed = false
    val expireTimeStr = map.get("expiryDate") as? String ?: throw IllegalArgumentException("expireTime is required")
    val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
    val expireTime = LocalDateTime.parse(expireTimeStr, formatter)
//    val expireTime = LocalDateTime.parse(expireTimeStr)

    return MemberCouponRequestDto(
        couponId = couponId,
        isUsed = isUsed,
        issuedTime = LocalDateTime.now(), // 현재 시간으로 설정
        expireTime = expireTime
    )
}