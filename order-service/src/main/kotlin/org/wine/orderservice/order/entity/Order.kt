package org.wine.orderservice.order.entity

import jakarta.persistence.*
import lombok.Getter
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant
import java.util.Date

@Entity
@Table(name = "ORDER_PRODUCT")
@Getter
@EntityListeners(AuditingEntityListener::class)
class Order (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID", nullable = false)
    val orderId : Long = 0,

    @Column(name = "MEMBER_ID", nullable = false)
    var memberId : Long,

    @Column(name = "RECEIVER_PHONE_NUMBER", nullable = false)
    var receiverPhoneNumber: String,

    @Column(name = "RECEIVER_NAME", nullable = false)
    var receiverName: String,

    @Column(name = "RECEIVER_ADDR" , nullable = false)
    var receiverAddr : String,

    @Column(name = "RECIVER_ADDR_DETAIL", nullable = false)
    var receiverAddrDetail : String,

    @Column(name = "TOTAL_PRICE", nullable = false )
    var totalPrice : Int,

    @Column(name = "FINAL_PRICE", nullable = false)
    var finalPrice : Int,

    @Column(name = "RSRV_DATE", nullable = true)
    var rsrvDate: String,

    @Column(name = "STATUS", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    var status : OrderStatus,

    @CreatedDate
    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    var createdAt: Instant = Instant.now(),

    @LastModifiedDate
    @Column(name = "UPDATED_AT", nullable = true)
    var updatedAt: Instant? = null
){
    fun approve() {
        this.status = OrderStatus.ORDER_COMPLETED
    }

    fun cancel(){
        this.status = OrderStatus.ORDER_FAILED
    }
}

