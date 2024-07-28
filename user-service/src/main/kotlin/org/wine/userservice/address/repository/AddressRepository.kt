package org.wine.userservice.address.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.wine.userservice.address.entity.Address

@Repository
interface AddressRepository : JpaRepository<Address, Long> {
    fun findByMemberUserId(userId: Long): List<Address>
}