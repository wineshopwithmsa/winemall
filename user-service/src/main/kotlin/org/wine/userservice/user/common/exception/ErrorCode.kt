package org.wine.userservice.user.common.exception

enum class ErrorCode(val code: String, val message: String) {
    USER_NOT_FOUND("USER_NOT_FOUND", "User not found"),
    ALREADY_USER_EXIST("ALREADY_USER_EXIST", "Already User Exist"),
    INVALID_EMAIL_FORMAT("INVALID_EMAIL_FORMAT","Invalid email format"),

    MEMBER_COUPON_NOT_FOUND("MEMBER_COUPON_NOT_FOUND", "Member coupon not found"),
    INVALID_CREDENTIALS("INVALID_CREDENTIALS", "Invalid credentials"),
    USER_ALREADY_EXISTS("USER_ALREADY_EXISTS", "User already exists"),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", "Internal server error"),
    COUPON_EXPIRED("COUPON EXPIRED","coupon expired"),
    COUPON_ALREADY_USED("COUPON_ALREADY_USED", "coupon already used")

}