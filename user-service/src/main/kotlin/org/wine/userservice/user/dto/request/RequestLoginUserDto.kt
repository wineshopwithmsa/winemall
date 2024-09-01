package org.wine.userservice.user.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.wine.userservice.common.validator.ValidationGroup
import java.io.Serializable

data class RequestLoginUserDto(
    @field:NotBlank(message = "이메일은 입력해주세요")
    var email: String ,
    @NotNull(message = "비밀번호를 입력해주세요", groups = [ValidationGroup.NotBlankGroup::class])
    @field:NotBlank(message = "비밀번호를 입력해주세요")
    var password: String,
) : Serializable