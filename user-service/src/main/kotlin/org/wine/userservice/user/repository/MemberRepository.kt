package org.wine.userservice.user.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.wine.userservice.user.entity.Member

@Repository
interface MemberRepository : JpaRepository<Member, Long> {
    //    User findByUserId(String userId);
    fun findByEmail(email: String?): Member?
}
