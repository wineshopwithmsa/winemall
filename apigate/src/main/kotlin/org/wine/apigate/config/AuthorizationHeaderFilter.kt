package org.wine.apigate.config

import io.jsonwebtoken.Jwts
import lombok.extern.slf4j.Slf4j
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.core.env.Environment
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
@Slf4j
class AuthorizationHeaderFilter(private val env: Environment) : AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config>(Config::class.java) {

    class Config

    override fun apply(config: Config): GatewayFilter {
        return GatewayFilter { exchange, chain ->
            val request = exchange.request
            if (!request.headers.containsKey(HttpHeaders.AUTHORIZATION)) {
                return@GatewayFilter onError(exchange, "no authorization header", HttpStatus.UNAUTHORIZED)
            }
            val authorizationHeader = request.headers[HttpHeaders.AUTHORIZATION]?.get(0)
            val jwt = authorizationHeader?.replace("Bearer", "")?.trim()
            if (jwt == null || !isJwtValid(jwt)) {
                return@GatewayFilter onError(exchange, "JWT token is not valid", HttpStatus.UNAUTHORIZED)
            }
            chain.filter(exchange)
        }
    }

    private fun isJwtValid(jwt: String): Boolean {
        var returnValue = true
        var subject: String? = null
        try {
            subject = Jwts.parser().setSigningKey(env.getProperty("token.secret"))
                .parseClaimsJws(jwt)
                .body
                .subject
        } catch (ex: Exception) {
//            log.info("Exception occurred while parsing JWT")
            returnValue = false
        }
        if (subject.isNullOrEmpty()) returnValue = false

        return returnValue
    }

    private fun onError(exchange: ServerWebExchange, err: String, httpStatus: HttpStatus): Mono<Void> {
        val response = exchange.response
        response.statusCode = httpStatus
//        Log.error(err)
        return response.setComplete()
    }
}