package org.wine.productservice.wine.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.wine.productservice.wine.entity.Category

@Repository
interface CategoryRepository : JpaRepository<Category, Long> {

}
