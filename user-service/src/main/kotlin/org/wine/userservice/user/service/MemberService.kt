package org.wine.userservice.user.service

import ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.wine.userservice.user.common.exception.CustomException
import org.wine.userservice.user.common.exception.ErrorCode
import org.wine.userservice.user.dto.request.RequestLoginUserDto
import org.wine.userservice.user.dto.request.UserRequestDto
import org.wine.userservice.user.dto.response.JwtResponseDTO
import org.wine.userservice.user.dto.response.MemberResponseDto
import org.wine.userservice.user.entity.Member
import org.wine.userservice.user.entity.MemberRole
import org.wine.userservice.user.jwt.JwtService
import org.wine.userservice.user.repository.MemberRepository

@Service
class MemberService @Autowired constructor(
    private val memberRepository: MemberRepository,
    private val authenticationManager: AuthenticationManager,
    private val jwtService: JwtService,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder
) {
    private val passwordEncoder = BCryptPasswordEncoder()
    private val logger = LoggerFactory.getLogger(javaClass)

    private fun checkIfEmailExistsCheck(email: String?) {
        if (email != null && memberRepository.countByEmail(email) > 0) {
            logger.error(ErrorCode.ALREADY_USER_EXIST.toString())
            throw CustomException(ErrorCode.ALREADY_USER_EXIST)
        }
    }
    suspend fun signUp(userRequest: UserRequestDto): MemberResponseDto {
//        validateEmailFormatCheck(userRequest.email)
        checkIfEmailExistsCheck(userRequest.email)

        val userRole = createUserRole(userRequest.role)
        val encodedPassword = passwordEncoder.encode(userRequest.password ?: "")

        val user = Member(
            email = userRequest.email,
            password = encodedPassword,
            nickName = userRequest.nickName,
            roles = mutableSetOf(userRole)
        )

        val savedUser = memberRepository.save(user)
        return MemberResponseDto.fromResponseDtoUser(savedUser)
    }



    suspend fun toLogin(authRequestDTO: RequestLoginUserDto): ApiResponse<Any> {
        val member = withContext(Dispatchers.IO) {
            memberRepository.findByEmail(authRequestDTO.email)
        }
        val accessToken = jwtService.generateToken(member?.getUserId().toString())

        return ApiResponse.Success(
            data = JwtResponseDTO(
                accessToken = accessToken
            ))
    }

    suspend fun getUserInfo(memberId: Long): MemberResponseDto {
        val userInfo = memberRepository.findById(memberId).orElseThrow {
            logger.error("getUserInfo error {}",ErrorCode.USER_NOT_FOUND)
            CustomException(ErrorCode.USER_NOT_FOUND)
        }
        return MemberResponseDto.fromResponseDtoUser(userInfo)
    }

    private fun createUserRole(role: String): MemberRole {
        return MemberRole().apply {
            id = if (role == "ROLE_USER") 1 else 2
            name = role
        }
    }

    private fun authenticateUser(email: String, password: String): Authentication {
//        return authenticationManager.authenticate(
//            UsernamePasswordAuthenticationToken(email, password)
//        )
        val authenticationToken  = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(email, password)
        )
        val authentication: Authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken)

        logger.info("authentication== {} ",authentication)
        SecurityContextHolder.getContext().authentication = authentication
        return authentication
    }
}