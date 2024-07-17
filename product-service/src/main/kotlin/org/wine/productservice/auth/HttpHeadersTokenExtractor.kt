package org.wine.productservice.auth

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component

@Component
class HttpHeadersTokenExtractor(private val httpServletRequest: HttpServletRequest) : TokenExtractor {
    override fun extract(): String? {
        val authorizationHeader = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION)
        return if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            authorizationHeader.substring(7)
        } else {
            null
        }
    }
}
