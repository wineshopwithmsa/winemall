package org.wine.productservice.wine.entity

import java.time.Instant
import jakarta.persistence.*
import lombok.Getter
import org.hibernate.annotations.ColumnDefault
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.math.BigDecimal

@Entity
@Table(name = "wine")
@Getter
@EntityListeners(AuditingEntityListener::class)
class Wine(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wine_id", nullable = false)
    val wineId: Long = 0L,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "region_id", nullable = false)
    var region: Region,

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    var description: String,

    @Column(name = "alcohol_percentage", nullable = false, precision = 3, scale = 1)
    @ColumnDefault("0.0")
    var alcoholPercentage: BigDecimal,

    @Column(name = "registrant_id", nullable = false)
    var registrantId: Long = 0L,

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: Instant = Instant.now(),

    @LastModifiedDate
    @Column(name = "updated_at", nullable = true)
    var updatedAt: Instant? = null,

    // TODO: 단수형 사용이 맞나 검토.
    @OneToMany(mappedBy = "wine", fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST])
    var category: MutableSet<WineCategory> = mutableSetOf()
) {
    fun tagWithCategories(newCategories: Set<Category>) {
        category.removeIf { it.category !in newCategories }

        newCategories.forEach { c ->
            if (category.none { it.category == c }) {
                category.add(WineCategory(wine = this, category = c))
            }
        }
    }
}