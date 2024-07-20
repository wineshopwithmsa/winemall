package org.wine.couponservice.coupon.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.wine.couponservice.coupon.entity.Coupon

interface CouponRepository : JpaRepository<Coupon, Long>
