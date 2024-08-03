package org.wine.orderservice.order.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.wine.orderservice.order.entity.OrderDetail

interface OrderDetailRepository: JpaRepository<OrderDetail, Long> {
}