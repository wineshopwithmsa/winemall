package org.wine.orderservice.order.feign

import org.springframework.cloud.openfeign.FeignClient

@FeignClient(name = "productSercieFeign", url = "\${feign.product-service-feign.url}")
interface ProductServiceFeign {
}