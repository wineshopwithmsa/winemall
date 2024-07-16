package org.wine.apigate.config

import org.slf4j.LoggerFactory
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.util.Objects

@Component
class AuthorizationHeaderFilter(
//    private val jwtUtil: JwtUtil,
//    private val redisUtil: RedisUtil
) : AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config>(Config::class.java) {

    companion object {
        private val log = LoggerFactory.getLogger(AuthorizationHeaderFilter::class.java)
    }

    class Config

    override fun apply(config: Config): GatewayFilter {
        return GatewayFilter { exchange, chain ->
            val request = exchange.request

            // HTTP 요청 헤더에서 Authorization 헤더를 가져옴
            val headers = request.headers
            if (!headers.containsKey(HttpHeaders.AUTHORIZATION)) {
                return@GatewayFilter onError(exchange, "HTTP 요청 헤더에 Authorization 헤더가 포함되어 있지 않습니다.", HttpStatus.UNAUTHORIZED)
            }

            val authorizationHeader = Objects.requireNonNull(headers[HttpHeaders.AUTHORIZATION])?.get(0)

            // JWT 토큰 가져오기
            val accessToken = authorizationHeader?.replace("Bearer ", "")
            log.info("[*] Token exists")

            // JWT 토큰 유효성 검사
//            jwtUtil.validateAccessToken(accessToken)

            // logout 처리된 accessToken
//            if (redisUtil.get(accessToken) != null && redisUtil.get(accessToken) == "logout") {
//                log.info("[*] Logout accessToken")
//                return@GatewayFilter onError(exchange, "로그아웃된 토큰입니다.", HttpStatus.UNAUTHORIZED)
//            }

            // JWT 토큰에서 사용자 email 추출
//            val subject = jwtUtil.getEmail(accessToken)

            // 사용자 email를 HTTP 요청 헤더에 추가하여 전달
            val newRequest = request.mutate()
                .header("email", "csj")
                .build()

            chain.filter(exchange.mutate().request(newRequest).build())
        }
    }

    // Mono(단일 값), Flux(다중 값) -> Spring WebFlux
    private fun onError(exchange: ServerWebExchange, errorMsg: String, httpStatus: HttpStatus): Mono<Void> {
        log.error(errorMsg)

        val response = exchange.response
        response.statusCode = httpStatus

        return response.setComplete()
    }
}