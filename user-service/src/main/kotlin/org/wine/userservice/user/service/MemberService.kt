package org.wine.userservice.user.service

import ApiResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.wine.userservice.user.dto.response.JwtResponseDTO
import org.wine.userservice.user.dto.request.RequestLoginUserDto
import org.wine.userservice.user.dto.request.UserRequestDto
import org.wine.userservice.user.dto.response.MemberResponseDto
import org.wine.userservice.user.entity.Member
import org.wine.userservice.user.entity.MemberRole
import org.wine.userservice.user.jwt.JwtService
import org.wine.userservice.user.repository.MemberRepository

@Service
class MemberService @Autowired constructor(
    private val memberRepository: MemberRepository,
    private val authenticationManager: AuthenticationManager,
    private val jwtService: JwtService
){
    fun signUp(userRequest: UserRequestDto): MemberResponseDto {
        val encoder = BCryptPasswordEncoder()
        val userRole = MemberRole().apply {
            id = if (userRequest.role == "ROLE_USER") 1 else 2
            name = userRequest.role
        }
        val roleSet = mutableSetOf(userRole)
        val encodedPassword = encoder.encode(userRequest?.password ?: "")

        val user = Member(
            email = userRequest?.email!!,
            password = encodedPassword,
            nickName = userRequest.nickName,
            roles = roleSet
        )
        val rtnUser = memberRepository.save(user)
        return MemberResponseDto.fromResponseDtoUser(rtnUser)
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
                var member = memberRepository.findByEmail(authRequestDTO.email)
                if (member != null) {
                        return ApiResponse.Success(data= JwtResponseDTO(
                            accessToken = jwtService.generateToken(member.getUserId().toString())
                        )
                    )
                }
            } else {
                throw UsernameNotFoundException("user not found")
            }
        }
        return ApiResponse.Success(data="")
    }

    fun getUserInfo(memberId: Long): MemberResponseDto {
        val userInfo = memberRepository.findById(memberId).orElseThrow {
            throw NoSuchElementException("존재하지 않는 유저입니다")
        }
        return MemberResponseDto.fromResponseDtoUser(userInfo)
    }
}