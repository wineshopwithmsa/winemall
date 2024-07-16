package org.wine.productservice.product.controller

import com.netflix.discovery.converters.Auto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.wine.productservice.product.service.JwtTokenProvider

@RestController
@RequestMapping("/api/product")
class ProductController {
    @Autowired lateinit var jwtTokenProvider: JwtTokenProvider

    @GetMapping("/v1")
    fun getTest(@RequestHeader headers: HttpHeaders): String {
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