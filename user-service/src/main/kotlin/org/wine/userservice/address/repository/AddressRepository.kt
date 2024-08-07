package org.wine.userservice.address.repository

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import org.wine.userservice.address.entity.Address
import reactor.core.publisher.Flux

@Repository
interface AddressRepository : ReactiveCrudRepository<Address, Long> {
    fun findByMemberUserId(userId: Long): Flux<Address>
}