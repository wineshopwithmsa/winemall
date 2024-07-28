package org.wine.couponservice.coupon.entity

import jakarta.persistence.*
import org.wine.couponservice.coupon.dto.response.CouponResponseDTO
import java.sql.Timestamp
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "coupon")
data class Coupon(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    val couponId: Long = 0,

    @Column(name = "code", nullable = false, unique = true, length = 50)
    val code: String,

    @Column(name = "description", length = 255)
    val description: String,

    @Column(name = "discount_type", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    val discountType: DiscountType,

    @Column(name = "discount_value", nullable = false)
    val discountValue: Double,

    @Column(name = "expiry_date", nullable = false)
    var expiryDate: LocalDateTime = LocalDateTime.now()
){
    constructor() : this(0, "", "", DiscountType.PERCENTAGE, 0.0, LocalDateTime.now())
}

enum class DiscountType {
    PERCENTAGE,
    AMOUNT
}