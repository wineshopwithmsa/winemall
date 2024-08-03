package org.wine.orderservice.order.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.wine.orderservice.order.entity.Order

@Repository
interface OrderRepository : JpaRepository<Order, Long> {
    fun findAllByMemberIdOrderByOrderIdDesc(memberId: Long): List<Order>
}