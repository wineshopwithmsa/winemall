package org.wine.userservice.usercoupon.entity

import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@Table(name = "member_coupoon")
@EntityListeners(AuditingEntityListener::class)
class MemberCoupon (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_coupon_id", nullable = false)
    val memberCouponId: Long = 0L,

    @Column(name = "coupon_id", nullable = false)
    var couponId: Long,

    @Column(name = "is_used", nullable = false)
    var isUsed: Boolean,

    @Column(name = "issued_time", nullable = false)
    var issuedTime: LocalDateTime,

    @Column(name = "expire_time", nullable = false)
    var expireTime: LocalDateTime,

){
    constructor() : this(
        memberCouponId = 0L,
        couponId = 0L, // 기본값 설정
        isUsed = false, // 기본값 설정
        issuedTime = LocalDateTime.now(),
        expireTime = LocalDateTime.now()
    )
}