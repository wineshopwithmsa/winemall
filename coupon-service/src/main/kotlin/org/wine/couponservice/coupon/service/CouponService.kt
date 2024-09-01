package org.wine.couponservice.coupon.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.wine.couponservice.common.service.RedisService
import org.wine.couponservice.common.utils.DateTimeUtils.laterNHours
import org.wine.couponservice.coupon.dto.request.CouponRequestDTO
import org.wine.couponservice.coupon.dto.response.CouponResponseDTO
import org.wine.couponservice.coupon.entity.Coupon
import org.wine.couponservice.coupon.entity.DiscountType
import org.wine.couponservice.coupon.repository.CouponRepository
import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.TimeUnit

@Service
class CouponService(
    private val couponRepository: CouponRepository,
    private val redisService: RedisService,

) {
    private val COUPON_KEY_PREFIX = "coupon:"

    fun createCoupon(couponRequestDTO: CouponRequestDTO): CouponResponseDTO {
        val coupon = Coupon(
            code = couponRequestDTO.code,
            description = couponRequestDTO.description,
            discountType = couponRequestDTO.discountType,
            discountValue = couponRequestDTO.discountValue,
            expiryDate = couponRequestDTO.expiryDate,
            cnt = couponRequestDTO.cnt
        )
        val savedCoupon = couponRepository.save(coupon)
        return savedCoupon.toResponseDTO()
    }

    fun getCouponById(id: Long): CouponResponseDTO {
        val coupon = couponRepository.findById(id).orElseThrow { NoSuchElementException("Coupon not found") }
        return coupon.toResponseDTO()
    }

    fun getAllCoupons(): List<CouponResponseDTO> {
        return couponRepository.findAll().map { it.toResponseDTO() }
    }
    fun Coupon.toResponseDTO(): CouponResponseDTO {
        return CouponResponseDTO(
            couponId = this.couponId,
            code = this.code,
            description = this.description,
            discountType = this.discountType,
            discountValue = this.discountValue,
            expiryDate = this.expiryDate,
            cnt = this.cnt
        )
    }

    fun createFcfsCoupon(cid: String) {
        val expirationTime =  TimeUnit.HOURS.toMillis(1)
        val coupon = Coupon(
            code = cid,
            description = "제품 10%할인 쿠폰",
            discountType = DiscountType.PERCENTAGE,
            discountValue = 10.0,
            expiryDate = laterNHours(1),
            cnt = 100
        )

        redisService.putData("coupon:$cid", coupon, expirationTime)
//        val saved: Optional<MyUserDto> = redisService.getData("my", MyUserDto::class.java)
    }

    fun createCouponLimit( nums: Int) {
        val coupons = (1..nums).map {
            Coupon(
                code = UUID.randomUUID().toString(),
                description = "test",
                discountType = DiscountType.PERCENTAGE,
                discountValue = 10.0,
                expiryDate = LocalDateTime.now().plusDays(1),
                cnt = 1 //,
            )
        }
        couponRepository.saveAll(coupons)

        // Redis에 쿠폰 저장
        coupons.forEach { coupon ->
            redisService.pushToList(COUPON_KEY_PREFIX + "available", coupon.code)
        }
    }
    @Transactional
    fun updateCouponByCode(code: String):Coupon {
//        val coupon = couponRepository.findByCouponCode(code)
//        coupon.isIssued = true
        couponRepository.updateUserCouponByCode(code)
        val coupon = couponRepository.findByCouponCode(code)
        return coupon
    }


}