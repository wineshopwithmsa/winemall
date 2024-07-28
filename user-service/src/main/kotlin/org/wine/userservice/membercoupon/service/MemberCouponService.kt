package org.wine.userservice.membercoupon.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.wine.userservice.membercoupon.client.CouponGateway
import org.wine.userservice.membercoupon.dto.request.MemberCouponApplyRequestDto
import org.wine.userservice.membercoupon.dto.request.mapToMemberCouponRequestDto
import org.wine.userservice.membercoupon.dto.response.MemberCouponResponseDto
import org.wine.userservice.membercoupon.entity.MemberCoupon
import org.wine.userservice.membercoupon.repository.MemberCouponRepository
import org.wine.userservice.user.common.UserCommon
import org.wine.userservice.user.common.exception.CustomException
import org.wine.userservice.user.common.exception.ErrorCode
import org.wine.userservice.user.repository.MemberRepository
import java.time.LocalDateTime
import org.springframework.http.HttpHeaders

@Service
class MemberCouponService(private val couponGateway: CouponGateway,
                          private val memberCouponRepository: MemberCouponRepository,
                          private val memberRepository: MemberRepository,
                          private val userCommon: UserCommon
) {
    fun getCoupons(): List<Map<String, Any>>? {
        val response = couponGateway.getCoupons()
        return response?.get("data") as? List<Map<String, Any>>
    }
    fun getCoupon(couponId:Long): Map<String, Any>? {
        val response = couponGateway.getCoupon(couponId)
        return response?.get("data") as? Map<String, Any>
    }

    fun applyCouponMember(memberCouponApplyRequestDto: MemberCouponApplyRequestDto):MemberCouponResponseDto {
        val memberId = memberCouponApplyRequestDto.memberId
        val couponId = memberCouponApplyRequestDto.couponId

        val member = memberRepository.findById(memberId).orElseThrow {
            CustomException(ErrorCode.USER_NOT_FOUND)
        }
        val couponInfo = getCoupon(couponId)
        val memberCouponReq = mapToMemberCouponRequestDto(couponInfo)
        val memberCoupon = MemberCoupon(memberCouponReq,member)
        val savedMemberCoupon = memberCouponRepository.save(memberCoupon)

        return MemberCouponResponseDto.mapToMemberCouponDto(savedMemberCoupon)
    }

    @Transactional
    fun useCouponMember(memberCouponId: Long): MemberCouponResponseDto {

        val memberCoupon = memberCouponRepository.findById(memberCouponId).orElseThrow {
            CustomException(ErrorCode.MEMBER_COUPON_NOT_FOUND)
        }
        //만료시간 체크
        if(memberCoupon.expireTime < LocalDateTime.now()){
            throw CustomException(ErrorCode.COUPON_EXPIRED)
        }
        //사용여부 체크
        if(memberCoupon.isUsed){
            throw CustomException(ErrorCode.COUPON_ALREADY_USED)
        }
        memberCoupon.isUsed = true
        memberCoupon.usedTime = LocalDateTime.now()

        return MemberCouponResponseDto.mapToMemberCouponDto(memberCoupon)
    }

    fun getCouponMember(headers: HttpHeaders): Any? {
        val userId = userCommon.getJwtAccount(headers).toLong()

        val membercoupons = memberCouponRepository.findByMemberUserId(userId)
        return MemberCouponResponseDto.mapToMemberCouponDto(membercoupons)
    }
}