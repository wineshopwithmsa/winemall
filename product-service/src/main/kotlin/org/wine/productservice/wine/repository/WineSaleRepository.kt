package org.wine.productservice.wine.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository
import org.wine.productservice.wine.entity.Wine
import org.wine.productservice.wine.entity.WineSale

@Repository
interface WineSaleRepository : JpaRepository<WineSale, Long>, JpaSpecificationExecutor<Wine> {
    fun findAllByWineSaleIdIn(windeIdList: List<Long>) : List<WineSale>
}