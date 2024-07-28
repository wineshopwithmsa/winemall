package org.wine.orderservice.order.dto

data class Response <T>(
    val message: String ,
    val status : Int,
    val data : T
)