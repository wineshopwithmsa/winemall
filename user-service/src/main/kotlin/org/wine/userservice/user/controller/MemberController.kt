package org.wine.userservice.user.controller

import ApiResponse
import jakarta.validation.Valid
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.wine.userservice.user.common.UserCommon
import org.wine.userservice.user.dto.request.RequestLoginUserDto
import org.wine.userservice.user.dto.request.UserRequestDto
import org.wine.userservice.user.dto.response.MemberResponseDto
import org.wine.userservice.user.service.MemberService

@RestController
@RequestMapping("/api/member")
@Slf4j
class MemberController @Autowired constructor (
    private val memberService: MemberService,
    private val userCommon: UserCommon
):MemberApi{
    private val logger = LoggerFactory.getLogger(javaClass)
    @PostMapping("/v1/save")
    override suspend fun signUp(@Valid @RequestBody userRequest: UserRequestDto): ResponseEntity<MemberResponseDto> {
        val userResponse = memberService.signUp(userRequest)
        return ResponseEntity.ok(userResponse)
    }
//    @PostMapping("/v1/save")
//    suspend fun signUp(@RequestBody userRequest: UserRequestDto): ResponseEntity<MemberResponseDto> {
//        return try {
//            val userResponse = memberService.signUp(userRequest)
//            ResponseEntity.ok(userResponse)
//        } catch (e: Exception) {
//            e.printStackTrace()
//            logger.error("user save error")
//            ResponseEntity.status(500).build()
//        }
//    }

    @PostMapping("/v1/login")
    suspend fun authenticateAndGetToken(@RequestBody authRequestDTO: RequestLoginUserDto): ApiResponse<Any> {
        return memberService.toLogin(authRequestDTO)
    }

    @GetMapping("/v1/info")
    suspend fun getUserInfo(@RequestHeader headers: HttpHeaders): ApiResponse<Any> {
        val userId = userCommon.getJwtAccount(headers).toLong()
        return ApiResponse.Success(data = memberService.getUserInfo(userId))
    }



}