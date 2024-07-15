package org.wine.userservice.user.controller

import ApiResponse
import lombok.AllArgsConstructor
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.annotation.*
import org.wine.userservice.user.dto.JwtResponseDTO
import org.wine.userservice.user.dto.RequestLoginUserDto
import org.wine.userservice.user.dto.UserRequestDto
import org.wine.userservice.user.dto.UserResponseDto
import org.wine.userservice.user.jwt.JwtService
import org.wine.userservice.user.service.MemberService

@RestController
@RequestMapping("/user-service/api/member")
class MemberController {
    @Autowired lateinit var memberService: MemberService

    @PostMapping("/v1/save")
    fun saveUser(@RequestBody userRequest: UserRequestDto?): ResponseEntity<*> {
        try {
            val userResponse: UserResponseDto? = memberService?.saveUser(userRequest) ;
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

}