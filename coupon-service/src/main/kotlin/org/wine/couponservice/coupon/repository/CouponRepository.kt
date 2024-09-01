package org.wine.couponservice.coupon.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.wine.couponservice.coupon.entity.Coupon

@Repository
interface CouponRepository : JpaRepository<Coupon, Long> {
    @Query(value = "select * from coupon c where c.code =:code", nativeQuery = true)
    fun findByCouponCode(code: String): Coupon
    @Modifying
    @Query(value = "update coupon set  is_issued = true where code = :code ",nativeQuery = true)
    fun updateUserCouponByCode(code: String)
}
