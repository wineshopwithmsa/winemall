package org.wine.orderservice.common.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
class WebCleintConfig {
    @Bean
    public fun webClient(): WebClient{
        return WebClient.create()
    }

}