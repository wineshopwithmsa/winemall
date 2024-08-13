package org.wine.productservice.wine.entity

import java.time.Instant
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@Entity
@Table(name = "wine_sale")
@EntityListeners(AuditingEntityListener::class)
class WineSale(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wine_sale_id", nullable = false)
    val wineSaleId: Long = 0L,

    @Column(name = "seller_id", nullable = false)
    var sellerId: Long = 0L,

    @Column(name = "wine_id", nullable = false)
    val wineId: Long,

    @Column(name = "price", nullable = false)
    var price: Int = 0,

    @Column(name = "registered_quantity")
    var registeredQuantity: Int = 0,

    @Column(name = "stock_quantity")
    var stockQuantity: Int = 0,

    @Column(name = "sold_quantity")
    var soldQuantity: Int = 0,

    @Column(name = "sale_status")
    @Enumerated(EnumType.ORDINAL)
    var saleStatus: SaleStatus = SaleStatus.SALE_UPCOMING,

    @Column(name = "sale_start_time")
    var saleStartTime: Instant,

    @Column(name = "sale_end_time")
    var saleEndTime: Instant,

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: Instant = Instant.now(),

    @LastModifiedDate
    @Column(name = "updated_at")
    var updatedAt: Instant? = null
) {
    fun prepareSaleQuantities(quantity: Int) {
        this.registeredQuantity = quantity
        this.stockQuantity = quantity
        this.soldQuantity = 0
    }

    fun subtract(subtractCount: Int): Int {
        if (stockQuantity < subtractCount) throw Exception()

        return (price * subtractCount).also {
            stockQuantity -= subtractCount
        }
    }

    fun increment(count: Int) {
        this.stockQuantity += count
    }
}