package org.wine.orderservice.Auth.service

import jwt.JwtTokenProvider
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Service

@Service
class AuthService{
    private val jwtTokenProvider: JwtTokenProvider = JwtTokenProvider()

    fun getMemberIdFromToken(headers: HttpHeaders): Long {
         try {
             val authorizationHeader = headers.getFirst(HttpHeaders.AUTHORIZATION)

             if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                val token = authorizationHeader.substring(7)
                return jwtTokenProvider.getAccount(token).toLong()
             }
             else{
                throw IllegalArgumentException("Invalid account ID")
             }
         } catch (e: Exception) {
            throw IllegalArgumentException("Invalid JWT")
         }
    }
}