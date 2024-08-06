package org.wine.userservice.user.common

import jwt.JwtTokenProvider
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class UserCommon {

//    fun getJwtAccount(headers: HttpHeaders): Long {
//        val jwtTokenProvider = JwtTokenProvider()
//        val authorizationHeader = headers.getFirst(HttpHeaders.AUTHORIZATION)
//
//        return if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//            val jwt = authorizationHeader.substring(7) // "Bearer " 이후의 JWT 부분 추출
//            return jwtTokenProvider.getAccount(jwt).toLong()
//        } else {
//            return 0
//        }
//    }
    fun getJwtAccount(headers: HttpHeaders): Mono<Long> {
    val jwtTokenProvider = JwtTokenProvider()
        val authorizationHeader = headers.getFirst(HttpHeaders.AUTHORIZATION)
        return if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            val jwt = authorizationHeader.substring(7) // Extract the JWT part after "Bearer "
            Mono.fromCallable { jwtTokenProvider.getAccount(jwt).toLong() }
        } else {
            Mono.just(0L)
        }
    }
}