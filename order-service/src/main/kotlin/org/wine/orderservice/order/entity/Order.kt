package org.wine.orderservice.order.entity

import jakarta.persistence.*
import lombok.Getter
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@Entity
@Table(name = "ORDER")
@Getter
@EntityListeners(AuditingEntityListener::class)
class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID", nullable = false)
    val orderId : Long = 0L
}