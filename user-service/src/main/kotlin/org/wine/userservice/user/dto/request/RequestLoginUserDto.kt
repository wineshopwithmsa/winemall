package org.wine.userservice.user.dto.request

import java.io.Serializable

data class RequestLoginUserDto(
    var email: String,
    var password: String
) : Serializable