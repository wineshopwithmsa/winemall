package org.wine.productservice.auth

import jwt.JwtTokenProvider
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val tokenExtractor: TokenExtractor,
) {
    private val jwtTokenProvider: JwtTokenProvider = JwtTokenProvider()

    fun getAccountInfo(): String {
        val token = tokenExtractor.extract()
        return if (token != null) {
            try {
                return jwtTokenProvider.getAccount(token)
            } catch (e: Exception) {
                "Invalid JWT"
            }
        } else {
            "JWT is not present"
        }
    }
}
