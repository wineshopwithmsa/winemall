package org.wine.userservice.membercoupon.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.wine.userservice.membercoupon.entity.MemberCoupon

interface MemberCouponRepository : JpaRepository<MemberCoupon, Long> {
    fun findByMemberUserId(userId: Long): List<MemberCoupon>
}