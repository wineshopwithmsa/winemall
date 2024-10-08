package org.wine.userservice.user.common

import jwt.JwtTokenProvider
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component

@Component
class UserCommon {
    fun getJwtAccount2(token: String?): Long {
         val jwtTokenProvider = JwtTokenProvider()

        return if (token != null && token.startsWith("Bearer ")) {
            val jwt = token.substring(7) // "Bearer " 이후의 JWT 부분 추출
            jwtTokenProvider.getAccount(jwt).toLong()
        } else {
            0
        }
    }

    fun getJwtAccount(headers: HttpHeaders): Long {
        val jwtTokenProvider = JwtTokenProvider()
        val authorizationHeader = headers.getFirst(HttpHeaders.AUTHORIZATION)

        return if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            val jwt = authorizationHeader.substring(7) // "Bearer " 이후의 JWT 부분 추출
            return jwtTokenProvider.getAccount(jwt).toLong()
        } else {
            return 0
        }
    }
}