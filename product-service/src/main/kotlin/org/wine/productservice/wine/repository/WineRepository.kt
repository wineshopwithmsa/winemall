package org.wine.productservice.wine.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.wine.productservice.wine.entity.Wine
import java.util.*

@Repository
interface WineRepository : JpaRepository<Wine, Long>, JpaSpecificationExecutor<Wine>{
    @Query("SELECT w FROM Wine w LEFT JOIN FETCH w.categories WHERE w.wineId = :id")
    fun findByIdWithCategories(id: Long): Optional<Wine>
}