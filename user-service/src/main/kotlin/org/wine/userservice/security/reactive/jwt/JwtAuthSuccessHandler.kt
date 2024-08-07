package org.wine.userservice.security.reactive.jwt

import kotlinx.coroutines.reactor.mono
import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.server.WebFilterExchange
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import org.wine.userservice.common.response.HttpExceptionFactory.unauthorized
import reactor.core.publisher.Mono

@Component
class JwtAuthSuccessHandler(private val jwtService: JwtService) : ServerAuthenticationSuccessHandler {
    companion object {
        private const val AccessTime = 1000 * 60 * 15
        private const val RefreshTime = 1000 * 60 * 60 * 4
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    override fun onAuthenticationSuccess(
        webFilterExchange: WebFilterExchange, authentication: Authentication
    ): Mono<Void> = mono {
        val principal = authentication.principal ?: throw unauthorized()

        when (principal) {
            is User -> {
                val roles = principal.authorities.map { it.authority }.toTypedArray()

                val accessToken = jwtService.accessToken(principal.username, AccessTime, roles)
                val refreshToken = jwtService.refreshToken(principal.username, RefreshTime, roles)

                val exchange = webFilterExchange.exchange ?: throw unauthorized()

                log.info("User with roles: $roles")

                with(exchange.response.headers) {
                    setBearerAuth(accessToken)
                    set("Refresh-Token", refreshToken)
                }

            }
            else -> throw RuntimeException("Not User!") // TODO: separate exception
        }
        return@mono null
    }
}