package org.wine.userservice.address.entity

import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.wine.userservice.membercoupon.dto.request.MemberCouponRequestDto
import org.wine.userservice.user.entity.Member
import java.time.LocalDateTime

@Entity
@Table(name = "address")
@EntityListeners(AuditingEntityListener::class)
class Address (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id", nullable = false)
    val addressId: Long = 0L,

    @Column(name = "address", nullable = false)
    var address: String,

    @Column(name = "address_detail", nullable = false)
    var addressDetail: String,

    @Column(name = "postal_code", nullable = false, length = 50)
    var postalCode: String,

    @Column(name = "city", nullable = false, length = 100)
    var city: String,

    @ColumnDefault("0")
    @Column(name = "addr_order", nullable = false)
    var addrOrder: Int,

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    var member: Member

){
    constructor() : this(
        addressId = 0L,
        address = "",
        addressDetail = "",
        postalCode = "",
        city = "",
        addrOrder = 0,
        member = Member()
    )
}