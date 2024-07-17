package org.wine.productservice.product.controller

import ApiResponse
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import jwt.JwtTokenProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.wine.productservice.auth.AuthService
import org.wine.productservice.exception.UnauthorizedException
import org.wine.productservice.wine.dto.WineResponseDto
import org.wine.productservice.wine.entity.UserType
import org.wine.productservice.wine.service.WineService


@RestController
@RequestMapping("/api/wines")
class WineController @Autowired constructor(
    private val wineService: WineService,
    private val authService: AuthService
){
    @GetMapping("/health")
    fun health(): String {
        return "ok";
    }

    @GetMapping("/v1")
    fun getWines(@RequestHeader headers: HttpHeaders): ResponseEntity<ApiResponse<Any>> {
        val userRole = authService.getAccountInfo()

        return when (UserType.fromValue(userRole)) {
            UserType.SELLER -> {
                val wineResponse: List<WineResponseDto>? = wineService.getWinesForSeller()
                ResponseEntity.ok(ApiResponse.Success(status = 200, message = "Success", data = wineResponse))
            }
            // TODO: consumer
//            "CONSUMER" -> {
//                wineService.getWinesForConsumer()
//                val wineResponse: List<WineResponseDto>? = wineService.getWinesForConsumer()
//                ResponseEntity.ok(ApiResponse.Success(status = 200, message = "Success", data = wineResponse))
//            }
            else -> throw UnauthorizedException("Invalid user role")
        }
    }

    @GetMapping("/test/v1")
    fun getTest(@RequestHeader headers: HttpHeaders): String {
        val jwtTokenProvider = JwtTokenProvider()
        val authorizationHeader = headers.getFirst(HttpHeaders.AUTHORIZATION)

        return if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            val jwt = authorizationHeader.substring(7) // "Bearer " 이후의 JWT 부분 추출
            "JWT is present: ${jwtTokenProvider.getAccount(jwt).toString()}"
        } else {
            "JWT is not present"
        }
        return ""
    }
}