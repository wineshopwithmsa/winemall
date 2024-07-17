package org.wine.userservice.user.dto.response

import org.wine.userservice.user.entity.Member
import java.io.Serializable

data class MemberResponseDto(
    val userId: Long? = null,
    val email: String,
    val password: String?=null,
    val nickName: String? = null,
    val roles: String

) : Serializable {
    companion object {
        fun fromResponseDtoUser(user: Member): MemberResponseDto {
            return MemberResponseDto(
                email = user.getEmail(),
                nickName = user.getNickName(),
                roles = user.getRoles().joinToString(",") { it.name ?: "" }
            )
        }
    }
}