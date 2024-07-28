package org.wine.userservice.user.dto.response

import java.io.Serializable

/**
 * DTO for {@link org.wine.userservice.user.entity.Member}
 */
data class MemberDto(
    val userId: Long = 0L,
    val email: String = "",
    val password: String = "",
    val nickName: String? = null
) : Serializable