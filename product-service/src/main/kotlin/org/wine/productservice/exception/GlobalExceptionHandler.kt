package org.wine.productservice.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.wine.productservice.wine.exception.UnauthorizedException

open class NotFoundException(message: String) : RuntimeException(message)

@RestControllerAdvice
class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleUnauthorizedException(ex: UnauthorizedException): Map<String, Any> {
        return mapOf("error" to "Unauthorized", "message" to ex.message.orEmpty())
    }

    @ExceptionHandler(NotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleNotFoundException(ex: NotFoundException): ApiResponse.Error {
        return ApiResponse.Error(status = 404, message = ex.message ?: "Resource not found")
    }
}
