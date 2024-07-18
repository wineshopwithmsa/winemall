package org.wine.orderservice.order.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.wine.orderservice.order.entity.WineSale

@Repository
interface WineRepository : JpaRepository<WineSale, Long> {

}