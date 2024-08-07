package org.wine.userservice.user.controller

import ApiResponse
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.actuate.health.HttpCodeStatusMapper
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.*
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.buildAndAwait
import org.wine.userservice.user.common.UserCommon
import org.wine.userservice.user.common.exception.CustomException
import org.wine.userservice.user.dto.request.RequestLoginUserDto
import org.wine.userservice.user.dto.request.UserRequestDto
import org.wine.userservice.user.service.MemberService
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/member")
class MemberController @Autowired constructor(
    private val memberService: MemberService,
    private val userCommon: UserCommon,
    private val healthHttpCodeStatusMapper: HttpCodeStatusMapper
) {
    //    @PostMapping("/v1/save")
//    suspend fun signUp(@RequestBody userRequest: UserRequestDto): ResponseEntity<MemberResponseDto> {
//        return try {
//            val userResponse = memberService.signUp(userRequest)
//            ResponseEntity.ok(userResponse)
//        } catch (e: Exception) {
//            e.printStackTrace()
//            ResponseEntity.status(500).build()
//        }
//    }
    @PostMapping("/v2/save")
    suspend fun signUp(@RequestBody userRequest: UserRequestDto): ServerResponse {
        return try {
            val userResponse = memberService.signUp(userRequest).awaitSingle()
            ServerResponse.ok().bodyValueAndAwait(userResponse)
        } catch (e: Exception) {
            e.printStackTrace()
            ServerResponse.status(500).buildAndAwait()
        }
    }
//    @PostMapping("/v1/login")
//    fun authenticateAndGetToken(@RequestBody authRequestDTO: RequestLoginUserDto): ApiResponse<Any> {
//        return memberService.toLogin(authRequestDTO)
//    }
    @PostMapping("/v2/login")
    suspend fun authenticateAndGetToken(@RequestBody authRequestDTO: RequestLoginUserDto) {
        return memberService.toLogin(authRequestDTO)
    }
//    @GetMapping("/v1/info")
//    suspend fun getUserInfo(@RequestHeader headers: HttpHeaders): ApiResponse<Any> {
//        val userId = userCommon.getJwtAccount(headers).toLong()
//        return ApiResponse.Success(data = memberService.getUserInfo(userId))
//    }
    @GetMapping("/v2/info")
    suspend fun getUserInfo(@RequestHeader headers: HttpHeaders): ApiResponse<Any> {
        val userId = userCommon.getJwtAccount(headers).awaitSingle()
        val userInfo = memberService.getUserInfo(userId)
        return ApiResponse.Success(data = userInfo)
    }
//    @GetMapping("/v2/info")
//    suspend fun getUserInfo(@RequestHeader headers: HttpHeaders): Mono<ApiResponse<Any>> {
//        return userCommon.getJwtAccount(headers)
//            .flatMap { userId ->
//                memberService.getUserInfo(userId)
//                    .map { userInfo ->
//                        ApiResponse.Success(data = userInfo)
//                    }
//            }
//    }

}