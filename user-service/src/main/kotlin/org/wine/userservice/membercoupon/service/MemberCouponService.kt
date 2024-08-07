package org.wine.userservice.membercoupon.service

import kotlinx.coroutines.reactive.awaitSingle
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
import reactor.core.publisher.Mono

@Service
@Transactional(readOnly = true)
class MemberCouponService(private val couponGateway: CouponGateway,
                          private val memberCouponRepository: MemberCouponRepository,
                          private val memberRepository: MemberRepository,
                          private val userCommon: UserCommon
) {
    suspend fun getCoupons(): List<Map<String, Any>>? {
        val response = couponGateway.getCoupons()
        return response?.get("data") as? List<Map<String, Any>>
    }
//    fun getCoupon(couponId:Long): Mono<Map<String, Any>>? {
//        val response = couponGateway.getCoupon(couponId)
//        return Mono.fromCallable { response?.get("data") as? Map<String, Any> }
//    }
    fun getCoupon(couponId: Long): Mono<Map<String, Any>> {
        return Mono.fromCallable {
            val response = couponGateway.getCoupon(couponId)
            response?.get("data") as? Map<String, Any>
        }.flatMap { data ->
            if (data != null) {
                Mono.just(data)
            } else {
                Mono.empty()
            }
        }
    }

//    suspend fun applyCouponMember(memberCouponApplyRequestDto: MemberCouponApplyRequestDto):MemberCouponResponseDto {
//        val memberId = memberCouponApplyRequestDto.memberId
//        val couponId = memberCouponApplyRequestDto.couponId
//
//        val member = memberRepository.findById(memberId).orElseThrow {
//            CustomException(ErrorCode.USER_NOT_FOUND)
//        }
//        val couponInfo = getCoupon(couponId)
//        val memberCouponReq = mapToMemberCouponRequestDto(couponInfo)
//        val memberCoupon = MemberCoupon(memberCouponReq,member)
//        val savedMemberCoupon = memberCouponRepository.save(memberCoupon)
//
//        return MemberCouponResponseDto.mapToMemberCouponDto(savedMemberCoupon)
//    }
    suspend fun applyCouponMember(memberCouponApplyRequestDto: MemberCouponApplyRequestDto): Mono<MemberCouponResponseDto> {
        val memberId = memberCouponApplyRequestDto.memberId
        val couponId = memberCouponApplyRequestDto.couponId

        return memberRepository.findById(memberId)
            .switchIfEmpty(Mono.error(CustomException(ErrorCode.USER_NOT_FOUND)))
            .flatMap { member ->
                getCoupon(couponId)
                    .flatMap { couponInfo ->
                        val memberCouponReq = mapToMemberCouponRequestDto(couponInfo)
                        val memberCoupon = MemberCoupon(memberCouponReq, member)
                        memberCouponRepository.save(memberCoupon)
                    }
            }
            .flatMap { savedMemberCoupon ->
                MemberCouponResponseDto.mapToMemberCouponDtoEach(savedMemberCoupon)
            }
    }
//    suspend fun someMethod() {
//
//            applyCouponMember(memberCouponApplyRequestDto)
//                .subscribe(
//                    { memberCouponResponseDto -> println("Member coupon response: $memberCouponResponseDto") },
//                    { error -> println("Error: ${error.message}") }
//                )
//    }


//    @Transactional
//    suspend fun useCouponMember(memberCouponId: Long): MemberCouponResponseDto {
//
//        val memberCoupon = memberCouponRepository.findById(memberCouponId).orElseThrow {
//            CustomException(ErrorCode.MEMBER_COUPON_NOT_FOUND)
//        }
//        //만료시간 체크
//        if(memberCoupon.expireTime < LocalDateTime.now()){
//            throw CustomException(ErrorCode.COUPON_EXPIRED)
//        }
//        //사용여부 체크
//        if(memberCoupon.isUsed){
//            throw CustomException(ErrorCode.COUPON_ALREADY_USED)
//        }
//        memberCoupon.isUsed = true
//        memberCoupon.usedTime = LocalDateTime.now()
//
//        return MemberCouponResponseDto.mapToMemberCouponDto(memberCoupon)
//    }
    @Transactional
    suspend fun useCouponMember(memberCouponId: Long): Mono<MemberCouponResponseDto> {
        return memberCouponRepository.findById(memberCouponId)
            .switchIfEmpty(Mono.error(CustomException(ErrorCode.MEMBER_COUPON_NOT_FOUND)))
            .flatMap { memberCoupon ->
                // 만료시간 체크
                if (memberCoupon.expireTime < LocalDateTime.now()) {
                    return@flatMap Mono.error<MemberCoupon>(CustomException(ErrorCode.COUPON_EXPIRED))
                }
                // 사용여부 체크
                if (memberCoupon.isUsed) {
                    return@flatMap Mono.error<MemberCoupon>(CustomException(ErrorCode.COUPON_ALREADY_USED))
                }
                memberCoupon.isUsed = true
                memberCoupon.usedTime = LocalDateTime.now()

                memberCouponRepository.save(memberCoupon)
            }
            .flatMap { savedMemberCoupon ->
                MemberCouponResponseDto.mapToMemberCouponDtoEach(savedMemberCoupon)
            }
    }

    suspend fun getCouponMember(headers: HttpHeaders): Any? {
        val userId = userCommon.getJwtAccount(headers).awaitSingle()

//        val membercoupons = memberCouponRepository.findByMemberUserId(userId)
        return memberCouponRepository.findByMemberUserId(userId)
            .collectList()  // Flux<MemberCoupon>을 Mono<List<MemberCoupon>>로 변환
            .flatMapMany { memberCoupons -> MemberCouponResponseDto.mapToMemberCouponDto(memberCoupons) }

//        return MemberCouponResponseDto.mapToMemberCouponDto(membercoupons.flatMap {  })
    }
}