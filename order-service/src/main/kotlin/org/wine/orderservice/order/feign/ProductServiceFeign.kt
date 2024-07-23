package org.wine.orderservice.order.feign

import io.swagger.annotations.ApiResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.wine.orderservice.order.dto.OrderDto

@FeignClient(name = "productSercieFeign", url = "\${feign.product-service-feign.url}")
interface ProductServiceFeign {

    @GetMapping("/api/wines/v1/sale")
    fun getWineSale(@RequestBody wineSaleIdList: List<OrderDto>) : ApiResponse
}