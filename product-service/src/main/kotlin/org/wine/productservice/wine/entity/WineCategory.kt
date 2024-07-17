package org.wine.productservice.wine.entity

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "wine_category")
class WineCategory(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wine_category_id", nullable = false)
    val wineCategoryId: Long = 0L,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wine_id", nullable = false)
    val wine: Wine,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    val category: Category,

    @Column(name = "created_at", nullable = false)
    var createdAt: Instant,

    @Column(name = "updated_at", nullable = true)
    var updatedAt: Instant? = null
) {
    constructor() : this(
        wine = Wine(),
        category = Category(),
        createdAt = Instant.now()
    )
}