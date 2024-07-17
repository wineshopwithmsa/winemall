package org.wine.productservice.wine.entity

import java.math.BigDecimal
import java.time.Instant
import java.util.UUID
import jakarta.persistence.*
import lombok.Getter
import org.hibernate.annotations.ColumnDefault
import org.springframework.data.jpa.domain.support.AuditingEntityListener

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

    @Column(name = "wine_uuid", nullable = false)
    val uuid: UUID = UUID.randomUUID(),

    @Column(name = "name", nullable = false)
    var name: String = "",

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    var description: String = "",

    @Column(name = "alcohol_percentage", nullable = false, precision = 3, scale = 1)
    @ColumnDefault("0.0")
    var alcoholPercentage: BigDecimal,

    // TODO: 와인 판매자 정보 타입 결정 (Long or UUID)
    // 데이터베이스에만 우선 저장? 반환할 때는 필요하지 않을 것 같음
    @Column(name = "registrant_id", nullable = false)
    var registrantId: Long,

    @Column(name = "created_at", nullable = false)
    var createdAt: Instant = Instant.now(),

    @Column(name = "updated_at", nullable = true)
    var updatedAt: Instant? = null,

    @Column(name = "deleted_at", nullable = true)
    var deletedAt: Instant? = null,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "wine_category",
        joinColumns = [JoinColumn(name = "wine_id")],
        inverseJoinColumns = [JoinColumn(name = "category_id")]
    )
    var categories: Set<Category> = emptySet()
) {
    constructor() : this(
        region = Region(),
        alcoholPercentage = BigDecimal.ZERO,
        registrantId = 0L
    )
}