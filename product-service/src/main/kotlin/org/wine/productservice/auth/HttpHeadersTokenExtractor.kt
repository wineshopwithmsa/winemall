package org.wine.productservice.auth

import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component

@Component
class HttpHeadersTokenExtractor() : TokenExtractor {
    override fun extract(headers: HttpHeaders): String? {
        val authorizationHeader = headers.getFirst(HttpHeaders.AUTHORIZATION)
        return if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            authorizationHeader.substring(7)
        } else {
            null
        }
    }
}
