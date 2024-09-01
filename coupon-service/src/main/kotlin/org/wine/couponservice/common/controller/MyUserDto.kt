package org.wine.couponservice.common.controller

import lombok.Builder
import java.io.Serializable


@Builder
data class MyUserDto(
     val username: String? = null,
     val password: String? = null,
     val nickname: String? = null
) : Serializable