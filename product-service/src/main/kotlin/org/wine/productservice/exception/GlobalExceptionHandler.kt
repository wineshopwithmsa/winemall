package org.wine.productservice.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleUnauthorizedException(ex: UnauthorizedException): Map<String, Any> {
        return mapOf("error" to "Unauthorized", "message" to ex.message.orEmpty())
    }
}
