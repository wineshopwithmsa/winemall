package org.wine.orderservice.order.feign

import org.springframework.cloud.openfeign.FeignClient

@FeignClient(name = "couponSercieFeign", url = "\${feign.coupon-service-feign.url}")
interface CouponServiceFeign {
}