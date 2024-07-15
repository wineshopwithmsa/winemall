package org.wine.userservice.user.dto

import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import java.io.Serializable

data class RequestLoginUserDto(
    var email: String,
    var password: String
) : Serializable