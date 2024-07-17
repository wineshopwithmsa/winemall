package org.wine.userservice.user.dto.request

import java.io.Serializable

data class UserRequestDto(
    var email: String? = null,
    var password: String? = null,
    var nickName: String? = null,
    var role: String = ""
) : Serializable