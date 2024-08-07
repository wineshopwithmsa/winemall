package org.wine.userservice.user.repository

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import org.wine.userservice.user.entity.Member

@Repository
interface MemberRepository : ReactiveCrudRepository<Member, Long> {
    //    User findByUserId(String userId);
    fun findByEmail(email: String?): Member?
}
