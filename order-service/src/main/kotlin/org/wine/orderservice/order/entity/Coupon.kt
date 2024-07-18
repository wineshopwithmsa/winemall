package org.wine.orderservice.order.entity

import jakarta.persistence.*
import lombok.Getter
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@Entity
@Table(name = "COUPON")
@Getter
@EntityListeners(AuditingEntityListener::class)
class Coupon (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COUPON_ID", nullable = false)
    val couponId : Long = 0L,

    @Column(name = "DISCOUNT_VALUE")
    val discountValue : Int
)