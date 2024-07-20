package org.wine.orderservice.order.entity

import jakarta.persistence.*
import lombok.Getter
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@Entity
@Table(name = "WINE_SALE")
@Getter
@EntityListeners(AuditingEntityListener::class)
class WineSale (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WINE_SALE_ID", nullable = false)
    val wineSaleId : Long = 0L,

    @Column(name = "PRICE" , nullable = false)
    val price : Int
)
