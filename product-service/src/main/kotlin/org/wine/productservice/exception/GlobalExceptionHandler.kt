package org.wine.productservice.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.wine.productservice.wine.exception.CategoryNotFoundException
import org.wine.productservice.wine.exception.RegionNotFoundException
import org.wine.productservice.wine.exception.UnauthorizedException

@RestControllerAdvice
class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleUnauthorizedException(ex: UnauthorizedException): Map<String, Any> {
        return mapOf("error" to "Unauthorized", "message" to ex.message.orEmpty())
    }

    @ExceptionHandler(RegionNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleRegionNotFoundException(ex: RegionNotFoundException): ApiResponse.Error {
        return ApiResponse.Error(status = 404, message = ex.message ?: "Region not found")
    }

    @ExceptionHandler(CategoryNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleCategoryNotFoundException(ex: CategoryNotFoundException): ApiResponse.Error {
        return ApiResponse.Error(status = 404, message = ex.message ?: "Category not found")
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleGenericException(ex: Exception): ApiResponse.Error {
        return ApiResponse.Error(status = 500, message = "An unexpected error occurred")
    }
}
