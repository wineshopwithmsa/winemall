package org.wine.couponservice.coupon.service

import org.springframework.stereotype.Service
import org.wine.couponservice.coupon.dto.request.CouponRequestDTO
import org.wine.couponservice.coupon.dto.response.CouponResponseDTO
import org.wine.couponservice.coupon.entity.Coupon
import org.wine.couponservice.coupon.repository.CouponRepository
import java.util.*

@Service
class CouponService(private val couponRepository: CouponRepository) {

    fun createCoupon(couponRequestDTO: CouponRequestDTO): CouponResponseDTO {
        val coupon = Coupon(
            code = couponRequestDTO.code,
            description = couponRequestDTO.description,
            discountType = couponRequestDTO.discountType,
            discountValue = couponRequestDTO.discountValue,
            expiryDate = couponRequestDTO.expiryDate
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
            expiryDate = this.expiryDate
        )
    }
}