package org.wine.userservice.user.controller

import ApiResponse
import jakarta.validation.Valid
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.wine.userservice.common.annotation.CurrentUser
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
    @PreAuthorize("permitAll()")
    override suspend fun toLogin(@Valid @RequestBody authRequestDTO: RequestLoginUserDto): ApiResponse<Any> {
        return memberService.toLogin(authRequestDTO)
    }

    @GetMapping("/v1/info")
    override suspend fun getUserInfo(@CurrentUser userId: Long): ApiResponse<MemberResponseDto> {
        val getCurrentUserInfo = memberService.getUserInfo(userId)
        return ApiResponse.Success(data = getCurrentUserInfo)
    }
    @GetMapping("/v1/admin")
    suspend fun testAdmin(@CurrentUser userId:Long):Any{
        logger.info("userId  = {} ,",userId)
        return "asd"
    }
}