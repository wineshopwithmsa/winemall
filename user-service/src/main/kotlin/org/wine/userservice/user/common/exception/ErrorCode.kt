package org.wine.userservice.user.common.exception

enum class ErrorCode(val code: String, val message: String) {
    USER_NOT_FOUND("USER_NOT_FOUND", "User not found"),
    INVALID_CREDENTIALS("INVALID_CREDENTIALS", "Invalid credentials"),
    USER_ALREADY_EXISTS("USER_ALREADY_EXISTS", "User already exists"),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", "Internal server error")
}