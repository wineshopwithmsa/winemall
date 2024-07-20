package org.wine.productservice.config

import com.sun.jdi.request.InvalidRequestStateException
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.HandlerMethodValidationException
import org.wine.productservice.shared.validator.InvalidIdsException
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
        return ApiResponse.Error(
            status = HttpStatus.NOT_FOUND.value(),
            message = ex.message ?: "Resource not found"
        )
    }

    @ExceptionHandler(BadRequestException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBadRequestException(ex: BadRequestException): ApiResponse.Error {
        logger.debug { "Bad request: ${ex.message}" }
        return ApiResponse.Error(
            status = HttpStatus.BAD_REQUEST.value(),
            message = ex.message ?: "Bad request"
        )
    }

    @ExceptionHandler(InvalidRequestStateException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleGenericException(ex: InvalidRequestStateException): ApiResponse.Error {
        return ApiResponse.Error(
            status = HttpStatus.BAD_REQUEST.value(),
            message = ex.message ?: "Bad request"
        )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ApiResponse.Error {
        val errors = ex.bindingResult.fieldErrors.map { "${it.field}: ${it.defaultMessage}" }
        logger.debug { "Validation failed: $errors" }
        return ApiResponse.Error(
            status = HttpStatus.BAD_REQUEST.value(),
            message = "Validation failed $errors"
        )
    }

    @ExceptionHandler(NumberFormatException::class)
    fun handleNumberFormatException(ex: NumberFormatException): ResponseEntity<ApiResponse.Error> {
        logger.warn { "Invalid input format: ${ex.message}" }

        val errorMessage = "Invalid input format. Please provide valid positive integers."

        return ResponseEntity.badRequest().body(
            ApiResponse.Error(
                status = HttpStatus.BAD_REQUEST.value(),
                message = errorMessage
            )
        )
    }

    @ExceptionHandler(InvalidIdsException::class)
    fun handleInvalidIdsException(ex: InvalidIdsException): ResponseEntity<ApiResponse.Error> {
        logger.warn { "Invalid IDs for ${ex.fieldName}: ${ex.invalidIds}" }

        val errorMessage = "Invalid ${ex.fieldName}: ${ex.invalidIds.joinToString(", ")} must be positive integers"

        return ResponseEntity.badRequest().body(
            ApiResponse.Error(
                status = HttpStatus.BAD_REQUEST.value(),
                message = errorMessage
            )
        )
    }

    @ExceptionHandler(HandlerMethodValidationException::class)
    fun handleHandlerMethodValidationException(ex: HandlerMethodValidationException): ResponseEntity<ApiResponse.Error> {
        logger.warn { "Validation error: ${ex.message}" }
        val errorMessages = ex.allErrors.map { it.defaultMessage.orEmpty() }
        val detailedMessage = errorMessages.joinToString("; ")

        return ResponseEntity.badRequest().body(
            ApiResponse.Error(
                status = HttpStatus.BAD_REQUEST.value(),
                message = "Validation error: $detailedMessage"
            )
        )
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleGenericException(ex: Exception): ApiResponse.Error {
        logger.error { "Unhandled exception occurred : ${ex.message}"}
        return ApiResponse.Error(
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            message = "Internal Server Error"
        )
    }
}
