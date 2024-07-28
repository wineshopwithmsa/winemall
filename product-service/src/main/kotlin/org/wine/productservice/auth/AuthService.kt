package org.wine.productservice.auth

import jwt.JwtTokenProvider
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Service
import org.wine.productservice.wine.exception.UnauthorizedException

@Service
class AuthService(
    private val tokenExtractor: TokenExtractor,
) {
    private val jwtTokenProvider: JwtTokenProvider = JwtTokenProvider()

    fun getAccountId(headers: HttpHeaders): Long {
        val token = tokenExtractor.extract(headers)
            ?: throw UnauthorizedException("JWT is not present")

        return try {
            val accountId = jwtTokenProvider.getAccount(token)
            accountId.toLongOrNull() ?: throw IllegalArgumentException("Invalid account ID")
        } catch (e: Exception) {
            throw UnauthorizedException("Invalid JWT")
        }
    }
}
