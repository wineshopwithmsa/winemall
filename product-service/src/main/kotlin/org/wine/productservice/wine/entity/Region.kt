package org.wine.productservice.wine.entity

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "region")
class Region(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "region_id", nullable = false)
    val regionId: Long = 0L,

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "created_at", nullable = false)
    var createdAt: Instant,

    @Column(name = "updated_at", nullable = true)
    var updatedAt: Instant? = null,
) {
    constructor() : this(
        name = "",
        createdAt = Instant.now()
    )
}