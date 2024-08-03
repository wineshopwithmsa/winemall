package org.wine.orderservice.order.entity

import jakarta.persistence.*
import lombok.Getter
import org.springframework.data.jpa.domain.support.AuditingEntityListener


@Entity
@Table(name = "ORDER_DETAIL")
@Getter
@EntityListeners(AuditingEntityListener::class)
class OrderDetail (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_DETAIL_ID", nullable = false)
    val orderDetailId : Long = 0,

    @Column(name = "WINE_SALE_ID", nullable = false)
    var wineSaleId : Long,

    @Column(name = "QUANTITY", nullable = false)
    var quantity : Int,

    @Column(name = "ORIGIN_PRICE", nullable = false)
    var originPrice : Long,


    @Column(name = "SALE_PRICE", nullable = false)
    var salePrice : Long,

    @ManyToOne
    @JoinColumn(name = "ORDER_ID")
    var order : Order? = null
){
}