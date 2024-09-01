package org.wine.userservice.common.response

data class ErrorResponse(
    val status: Int,
    val message: String,
    val errors: Map<String, String>? = null
)