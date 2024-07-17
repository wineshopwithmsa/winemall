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
class MemberController {
    @Autowired lateinit var memberService: MemberService
    @Autowired lateinit var userCommon: UserCommon

    @PostMapping("/v1/save")
    fun saveUser(@RequestBody userRequest: UserRequestDto): ResponseEntity<*> {
        try {
            val userResponse: MemberResponseDto = memberService.saveUser(userRequest) ;
            return ResponseEntity.ok<Any>(userResponse)
        } catch (e: Exception) {
            e.printStackTrace()
            throw RuntimeException(e)
        }
    }
    @GetMapping("/v1")
    fun getUser():String{
        return "Test"
    }
    @PostMapping("/v1/login")
    fun AuthenticateAndGetToken(@RequestBody authRequestDTO: RequestLoginUserDto): ApiResponse<Any> {
        return memberService.toLogin(authRequestDTO)
    }
    @GetMapping("/v1/info")
    fun getUserInfo(@RequestHeader headers: HttpHeaders): ApiResponse<Any>{
        val result = userCommon.getJwtAccount(headers).toLong()
        return ApiResponse.Success(data = memberService.getUserInfo(result))
    }

}