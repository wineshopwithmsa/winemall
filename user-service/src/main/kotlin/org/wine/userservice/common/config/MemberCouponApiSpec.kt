package org.wine.userservice.common.config
import io.swagger.v3.oas.annotations.responses.ApiResponse as SwaggerApiResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import org.wine.userservice.membercoupon.dto.response.MemberCouponResponseDto

open class ApiResponseSuccess<T> {
    @field:Schema(example = "200")
    val status: Int = 0

    @field:Schema(example = "Success")
    val message: String = ""

    val data: T? = null
}

class GetMemberCoupons: ApiResponseSuccess<MemberCouponResponseDto>()

object MemberCouponApiSpec {
    @Target(AnnotationTarget.FUNCTION)
    @Retention(AnnotationRetention.RUNTIME)
    @Operation(
        summary = "유저 쿠폰 조회",
        description = "유저 쿠폰 조회 정보를 조회합니다.",
        responses = [
            SwaggerApiResponse(
                responseCode = "200",
                description = "유저 쿠폰 조회 성공",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = GetMemberCoupons::class))]
            ),

        ]
    )
    annotation class GetMemberCoupons
}