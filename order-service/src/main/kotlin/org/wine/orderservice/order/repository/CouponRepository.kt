package org.wine.orderservice.order.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.wine.orderservice.order.entity.Coupon
import org.wine.orderservice.order.entity.Order

@Repository
interface CouponRepository : JpaRepository<Coupon, Long> {
}