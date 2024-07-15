package org.wine.userservice.user.dto

import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.Getter
import lombok.NoArgsConstructor

data class JwtResponseDTO(
    val accessToken: String
)