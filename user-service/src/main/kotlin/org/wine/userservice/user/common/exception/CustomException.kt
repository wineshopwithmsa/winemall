package org.wine.userservice.user.common.exception

class CustomException(val errorCode: ErrorCode) : RuntimeException(errorCode.message)
