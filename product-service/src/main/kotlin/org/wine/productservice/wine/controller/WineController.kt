package org.wine.productservice.product.controller

import ApiResponse
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import jwt.JwtTokenProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.wine.productservice.wine.dto.WineCreateRequestDto
import org.wine.productservice.wine.dto.WineDto
import org.wine.productservice.wine.dto.WineUpdateRequestDto
import org.wine.productservice.wine.service.WineService


@RestController
@RequestMapping("/api/wines")
class WineController @Autowired constructor(
    private val wineService: WineService,
){
    @GetMapping("/health")
    fun health(): String {
        return "ok";
    }

    @PostMapping("/v1")
    fun createWine(@RequestBody requestDto: WineCreateRequestDto): ResponseEntity<ApiResponse<Any>> {
        val wine = wineService.addWine(requestDto)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.Success(status = 201, message = "Created", data = mapOf("wine" to wine)))
    }

    @GetMapping("/v1")
    fun getWines(@RequestHeader headers: HttpHeaders): ResponseEntity<ApiResponse<Any>> {
        val wines: List<WineDto>? = wineService.getWinesForSeller()
        return ResponseEntity.ok(ApiResponse.Success(status = 200, message = "Success", data = mapOf("wines" to wines)))
    }

    @PatchMapping("/v1/{wineId}")
    fun updateWine(@PathVariable wineId: Long, @RequestBody requestDto: WineUpdateRequestDto): ResponseEntity<ApiResponse<Any>> {
        val wine = wineService.updateWine(wineId, requestDto)
        return ResponseEntity.ok(ApiResponse.Success(status = 200, message = "Success", data = mapOf("wine" to wine)))
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