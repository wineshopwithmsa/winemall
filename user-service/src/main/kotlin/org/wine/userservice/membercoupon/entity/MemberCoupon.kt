package org.wine.userservice.membercoupon.entity

import jakarta.persistence.*
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.wine.userservice.membercoupon.dto.request.MemberCouponRequestDto
import org.wine.userservice.user.entity.Member
import java.time.LocalDateTime

@Entity
@Table(name = "member_coupon")
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

    @Column(name = "used_time", nullable = false)
    var usedTime: LocalDateTime = LocalDateTime.parse("9999-12-31T00:00:00"),

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    var member: Member

){
    constructor() : this(
        memberCouponId = 0L,
        couponId = 0L, // 기본값 설정
        isUsed = false, // 기본값 설정
        issuedTime = LocalDateTime.now(),
        expireTime = LocalDateTime.now(),
        usedTime = LocalDateTime.parse("9999-12-31T00:00:00"),
        member = Member()
    )
    constructor(dto: MemberCouponRequestDto, member: Member) : this(
        memberCouponId = 0L,
        couponId = dto.couponId,
        isUsed = dto.isUsed,
        issuedTime = dto.issuedTime,
        expireTime = dto.expireTime,
        usedTime = LocalDateTime.parse("9999-12-31T00:00:00"),
        member = member
    )

}