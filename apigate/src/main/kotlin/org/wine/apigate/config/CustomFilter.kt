package org.wine.apigate.config

import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
@Slf4j
class CustomFilter : AbstractGatewayFilterFactory<CustomFilter.Config>(Config::class.java) {

    private val log = LoggerFactory.getLogger(CustomFilter::class.java)

    override fun apply(config: Config): GatewayFilter {
        return GatewayFilter { exchange, chain ->
            val request: ServerHttpRequest = exchange.request
            val response: ServerHttpResponse = exchange.response

            log.info("Custom Pre filter: request id -> {}", request.id)

            chain.filter(exchange).then(Mono.fromRunnable {
                log.info("Custom Post filter: response code -> {}", response.statusCode)
            })
        }
    }

    class Config {
        // Put the configuration properties
    }
}