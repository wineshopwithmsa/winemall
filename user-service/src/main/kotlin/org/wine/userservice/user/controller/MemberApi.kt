package org.wine.userservice.user.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.wine.userservice.user.common.exception.ErrorCode
import org.wine.userservice.user.common.exception.InternalServerErrorResponse
import org.wine.userservice.user.dto.request.UserRequestDto
import org.wine.userservice.user.dto.response.MemberResponseDto

@Tag(name="Member Api", description = "유저 관련 api")
interface MemberApi {
    @Operation(summary = "회원가입 API", description = "신규 회원을 등록합니다")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "OK",
                content = [
                    Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = Schema(implementation = MemberResponseDto::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "500",
                description = "이미 회원이 존재합니다",
                content = [
                    Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
//                        schema = Schema(implementation = ErrorCode.ALREADY_USER_EXIST)
                        schema = Schema(implementation = InternalServerErrorResponse::class)
                    )
                ]
            )
        ]
    )
    suspend fun signUp(@RequestBody userRequest: UserRequestDto): ResponseEntity<MemberResponseDto>

}