package org.wine.couponservice.common


//@ControllerAdvice
//class GlobalExceptionHandler {
//    @ExceptionHandler(CustomException::class)
//    fun handleCustomExceptione(ex: CustomException): ResponseEntity<ErrorResponse> {
//        val errorCode = ex.errorCode
//        val errorResponse = ErrorResponse(errorCode.message,500,null)
//        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
//    }
//
//
//}