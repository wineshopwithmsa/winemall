package org.wine.orderservice.common.config

import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Configuration

@Configuration
@EnableFeignClients("org.wine.orderservice.order.feign")
class OpenFeignConfig{

}