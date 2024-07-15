package org.wine.userservice.user.dto

import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Getter
import lombok.NoArgsConstructor
import java.io.Serializable

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
class UserRequestDto : Serializable {
    var email: String? = null
    var password: String? = null
    var nickName: String? = null //    private Set<UserRole> roles;
}