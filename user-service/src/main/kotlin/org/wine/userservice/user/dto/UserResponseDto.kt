package org.wine.userservice.user.dto

import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Getter
import lombok.NoArgsConstructor
import org.wine.userservice.user.entity.Member
import java.io.Serializable

data class UserResponseDto(
    val userId: Long? = null,
    val email: String? = null,
    val password: String? = null,
    val nickName: String? = null
) : Serializable {
    companion object {
        fun fromResponseDtoUser(user: Member): UserResponseDto {
            return UserResponseDto(
                email = user.getEmail(),
                password = user.getPassword(),
                nickName = user.getNickName()
            )
        }
    }
}