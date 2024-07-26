package org.wine.userservice.common.config

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.wine.userservice.common.response.ErrorResponse
import org.wine.userservice.user.common.exception.CustomException

@ControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(CustomException::class)
    fun handleCustomExceptione(ex: CustomException): ResponseEntity<ErrorResponse> {
        val errorCode = ex.errorCode
        val errorResponse = ErrorResponse(errorCode.message,500,null)
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }


}