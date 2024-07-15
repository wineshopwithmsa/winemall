package org.wine.userservice.user.service

import ApiResponse
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.wine.userservice.user.dto.JwtResponseDTO
import org.wine.userservice.user.dto.RequestLoginUserDto
import org.wine.userservice.user.dto.UserRequestDto
import org.wine.userservice.user.dto.UserResponseDto
import org.wine.userservice.user.entity.Member
import org.wine.userservice.user.entity.MemberRole
import org.wine.userservice.user.jwt.JwtService
import org.wine.userservice.user.repository.MemberRepository

@Service
class MemberService {
    @Autowired lateinit var memberRepository: MemberRepository
    @Autowired lateinit var authenticationManager: AuthenticationManager
    @Autowired lateinit var jwtService: JwtService

    fun saveUser(userRequest: UserRequestDto?): UserResponseDto {

//        User user = User.fromRequestDto(userRequest);
        val encoder = BCryptPasswordEncoder()
        val userRole = MemberRole().apply {
            id = 1
            name = "ROLE_USER"
        }

        val roleSet = mutableSetOf(userRole)

        val encodedPassword = encoder.encode(userRequest?.password ?: "")

//        val user = Member().apply {
//            email = userRequest?.email!!
//            password = encodedPassword
//            roles = roleSet
//        }
        val user = Member(
            email = userRequest?.email!!,
            password = encodedPassword,
            nickName = userRequest.nickName,
            roles = roleSet
        )
        println(user.toString())

        val rtnUser = memberRepository?.save(user)
        return UserResponseDto.fromResponseDtoUser(rtnUser!!)
    }

    fun toLogin(authRequestDTO: RequestLoginUserDto): ApiResponse<Any> {
        val authentication: Authentication? = authenticationManager?.authenticate(
            UsernamePasswordAuthenticationToken(
                authRequestDTO.email,
                authRequestDTO.password
            )
        )
        if (authentication != null) {
            if (authentication.isAuthenticated) {
                //            RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequestDTO.getUsername());
                return ApiResponse.Success(data= JwtResponseDTO(
                    accessToken = jwtService.generateToken(authRequestDTO.email)
                )
                )
            } else {
                throw UsernameNotFoundException("user not found")
            }
        }
        return ApiResponse.Success(data="")
    }
}