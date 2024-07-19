package org.wine.productservice.exception

import com.sun.jdi.request.InvalidRequestStateException
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.wine.productservice.wine.exception.UnauthorizedException

private val logger = KotlinLogging.logger {}

open class NotFoundException(message: String) : RuntimeException(message)
open class BadRequestException(message: String) : RuntimeException(message)

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(UnauthorizedException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleUnauthorizedException(ex: UnauthorizedException): Map<String, Any> {
        return mapOf("error" to "Unauthorized", "message" to ex.message.orEmpty())
    }

    @ExceptionHandler(NotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleNotFoundException(ex: NotFoundException): ApiResponse.Error {
        logger.debug { "Not found: ${ex.message}" }
        return ApiResponse.Error(status = 404, message = ex.message ?: "Resource not found")
    }

    @ExceptionHandler(BadRequestException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBadRequestException(ex: BadRequestException): ApiResponse.Error {
        logger.debug { "Bad request: ${ex.message}" }
        return ApiResponse.Error(status = 400, message = ex.message ?: "Bad request")
    }

    @ExceptionHandler(InvalidRequestStateException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleGenericException(ex: InvalidRequestStateException): ApiResponse.Error {
        return ApiResponse.Error(status = 400, message = ex.message ?: "Bad request")
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ApiResponse.Error {
        val errors = ex.bindingResult.fieldErrors.map { "${it.field}: ${it.defaultMessage}" }
        logger.debug { "Validation failed: $errors" }
        return ApiResponse.Error(status = 400, message = "Validation failed $errors")
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleGenericException(ex: Exception): ApiResponse.Error {
        logger.error { "Unhandled exception occurred : ${ex.message}"}
        return ApiResponse.Error(status = 500, message = "Internal Server Error")
    }
}
