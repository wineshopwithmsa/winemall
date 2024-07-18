package org.wine.userservice.service

import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.wine.userservice.user.dto.request.RequestLoginUserDto
import org.wine.userservice.user.dto.request.UserRequestDto
import org.wine.userservice.user.dto.response.MemberResponseDto
import org.wine.userservice.user.entity.Member
import org.wine.userservice.user.entity.MemberRole
import org.wine.userservice.user.jwt.JwtService
import org.wine.userservice.user.repository.MemberRepository
import org.wine.userservice.user.service.MemberService

class MemberServiceTest {

    private lateinit var memberService: MemberService
    private val memberRepository: MemberRepository = mockk()
    private val authenticationManager: AuthenticationManager = mockk()
    private val jwtService: JwtService = mockk()

    @BeforeEach
    fun setUp() {
        memberService = MemberService(memberRepository, authenticationManager, jwtService)
    }

    @Test
    fun `회원가입은 새로운 유저를 생성해야 한다`() = runBlocking {
        // given
        val userRequest = UserRequestDto(
            email = "test@test.com",
            password = "password",
            nickName = "testUser",
            role = "ROLE_USER"
        )
        val member = Member(
            email = userRequest.email!!,
            password = userRequest.password!!,
            nickName = userRequest.nickName,
            roles = mutableSetOf(MemberRole(id = 1, name = "ROLE_USER"))
        )
        every { memberRepository.save(any()) } returns member

        // when
        val result = memberService.signUp(userRequest)

        // then
        assertNotNull(result)
        assertEquals(userRequest.email, result.email)
        assertEquals(userRequest.nickName, result.nickName)
    }

    @Test
    fun `로그인은 유저를 인증하고 토큰을 반환해야 한다`() = runBlocking {
        // given
        val authRequestDTO = RequestLoginUserDto(
            email = "test@example.com1221",
            password = "password"
        )
        val member = Member(
            email = authRequestDTO.email,
            password = "encodedPassword",
            nickName = "testUser",
            roles = mutableSetOf(MemberRole(id = 1, name = "ROLE_USER"))
        )
        val authentication: Authentication = mockk {
            every { isAuthenticated } returns true
        }
        every { authenticationManager.authenticate(any()) } returns authentication
        every { memberRepository.findByEmail(authRequestDTO.email) } returns member
        every { jwtService.generateToken(any()) } returns "jwtToken"

        // when
        val result = memberService.toLogin(authRequestDTO)

        // then
        assertNotNull(result)
        assertTrue(result is ApiResponse.Success)
//        assertEquals("jwtToken", (result as ApiResponse.Success).data.accessToken)
    }
}