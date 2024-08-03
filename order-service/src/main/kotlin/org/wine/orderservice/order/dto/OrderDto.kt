package org.wine.orderservice.order.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.wine.orderservice.order.entity.OrderStatus
import java.io.Serializable
import java.time.Instant

data class OrderDto(
    var orderId : Long,
    var memberId : Long,
    var receiverPhoneNumber: String,
    var receiverName: String,
    var receiverAddr : String,
    var receiverAddrDetail : String,
    var totalPrice : Int,
    var finalPrice : Int,
    var rsrvDate: String?,
    var status : OrderStatus,
    var createdAt: Instant,
    var updatedAt: Instant,
    var orderDetails: List<OrderDetailDto>?
) : Serializable