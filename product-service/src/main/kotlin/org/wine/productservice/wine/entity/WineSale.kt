package org.wine.productservice.wine.entity

import jakarta.persistence.*
import lombok.Getter
import org.springframework.data.jpa.domain.support.AuditingEntityListener


@Entity
@Table(name = "wine_sale")
@Getter
@EntityListeners(AuditingEntityListener::class)
class WineSale (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wine_sale_id", nullable = false)
    val wineSaleId: Long = 0L,

    @Column(name = "price", nullable = false)
    var price: Int
)
