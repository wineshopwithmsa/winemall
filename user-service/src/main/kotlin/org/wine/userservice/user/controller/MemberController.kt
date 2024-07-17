package org.wine.userservice.user.controller

import ApiResponse
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
class MemberController @Autowired constructor(
    private val memberService: MemberService,
    private val userCommon: UserCommon
) {
    @PostMapping("/v1/save")
    fun signUp(@RequestBody userRequest: UserRequestDto): ResponseEntity<MemberResponseDto> {
        return try {
            val userResponse = memberService.signUp(userRequest)
            ResponseEntity.ok(userResponse)
        } catch (e: Exception) {
            e.printStackTrace()
            ResponseEntity.status(500).build()
        }
    }

    @PostMapping("/v1/login")
    fun authenticateAndGetToken(@RequestBody authRequestDTO: RequestLoginUserDto): ApiResponse<Any> {
        return memberService.toLogin(authRequestDTO)
    }

    @GetMapping("/v1/info")
    fun getUserInfo(@RequestHeader headers: HttpHeaders): ApiResponse<Any> {
        val userId = userCommon.getJwtAccount(headers).toLong()
        return ApiResponse.Success(data = memberService.getUserInfo(userId))
    }

}