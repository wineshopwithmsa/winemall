package org.wine.userservice.user.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.wine.userservice.common.validator.ValidationGroup
import java.io.Serializable

data class UserRequestDto(
    @field:NotBlank(message = "이메일은 입력해주세요")
    var email: String ,
    @NotNull(message = "비밀번호를 입력해주세요", groups = [ValidationGroup.NotBlankGroup::class])
    var password: String,
    var nickName: String? = null,
    var role: String = ""
) : Serializable