package org.wine.userservice.user.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.io.Serializable

data class RequestLoginUserDto(
    @field:NotBlank
    @field:Size(min = 1, max = 255)
    var email: String,
    @field:NotBlank
    @field:Size(min = 1, max = 255)
    var password: String
) : Serializable