package org.wine.userservice.security.reactive.jwt

import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactor.mono
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus.UNAUTHORIZED
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.server.WebFilterExchange
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler
import org.springframework.stereotype.Component
import org.wine.userservice.common.response.HttpExceptionFactory
import reactor.core.publisher.Mono

@Component
class JwtAuthFailureHandler : ServerAuthenticationFailureHandler {

    override fun onAuthenticationFailure(
        webFilterExchange: WebFilterExchange, exception: AuthenticationException?
    ): Mono<Void> = mono {
        val exchange = webFilterExchange.exchange ?: throw HttpExceptionFactory.unauthorized()

        log.error("🍁err: ${UNAUTHORIZED.reasonPhrase}: ${exception?.message}")

        with(exchange.response) {
            statusCode = UNAUTHORIZED
            setComplete().awaitFirstOrNull()
        }
    }

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }
}