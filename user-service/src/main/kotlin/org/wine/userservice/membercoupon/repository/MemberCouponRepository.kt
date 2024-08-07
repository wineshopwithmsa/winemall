package org.wine.userservice.membercoupon.repository

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import org.wine.userservice.membercoupon.entity.MemberCoupon
import reactor.core.publisher.Flux

@Repository
interface MemberCouponRepository : ReactiveCrudRepository<MemberCoupon, Long> {
    fun findByMemberUserId(userId: Long): Flux<MemberCoupon>
}