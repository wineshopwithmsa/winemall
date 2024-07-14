package org.wine.orderservice.order.controller

import ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/order")
class OrderController {
    @GetMapping("/v1")
    fun getUsers(): ResponseEntity<ApiResponse<Any>> {

        val response = ApiResponse.Success(status = 200, message = "test mesasge", data = "test data")
        return ResponseEntity.ok(response)
    }
}