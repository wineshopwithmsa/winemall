package org.wine.productservice.config

import io.swagger.v3.oas.annotations.responses.ApiResponse as SwaggerApiResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.headers.Header
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody
import org.wine.productservice.wine.dto.*

open class ApiResponseSuccess<T> {
    @field:Schema(example = "200")
    val status: Int = 0

    @field:Schema(example = "Success")
    val message: String = ""

    val data: T? = null
}

open class ApiResponseCreated<T> {
    @field:Schema(example = "201")
    val status: Int = 0

    @field:Schema(example = "Created")
    val message: String = ""

    val data: T? = null
}

open class ApiResponseBadRequest {
    @field:Schema(example = "400")
    val status: Int = 0

    @field:Schema(example = "Bad Request")
    val message: String = ""
}

open class ApiResponseNotFound {
    @field:Schema(example = "400")
    val status: Int = 0

    @field:Schema(example = "Bad Request")
    val message: String = ""
}

open class ApiResponseServerError {
    @field:Schema(example = "500")
    val status: Int = 0

    @field:Schema(example = "Internal Server Error")
    val message: String = ""
}

/**
 * Wine
 */
class GetWines200: ApiResponseSuccess<WinesResponse>()
class GetWine200: ApiResponseSuccess<WineResponse>()
class CreateWine201: ApiResponseCreated<WineResponse>()
class UpdateWine200: ApiResponseCreated<WineResponse>()

/**
 * WineSale
 */
class GetWineSales200: ApiResponseSuccess<WineSalesResponse>()
class CreateWineSale201: ApiResponseCreated<WineSaleResponse>()
class UpdateWineSale200: ApiResponseCreated<WineSaleResponse>()

object WineApiSpec {
    @Target(AnnotationTarget.FUNCTION)
    @Retention(AnnotationRetention.RUNTIME)
    @Operation(
        summary = "와인 상세 조회",
        description = "와인 상세 정보를 조회합니다.",
        responses = [
            SwaggerApiResponse(
                responseCode = "200",
                description = "입력받은 wine id로 와인 상세 정보 조회 성공",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = GetWine200::class))]
            ),
            SwaggerApiResponse(
                responseCode = "404",
                description = "입력받은 wine id에 대한 와인을 찾을 수 없음.",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = ApiResponseNotFound::class))]
            ),
            SwaggerApiResponse(
                responseCode = "500",
                description = "서버 에러",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = ApiResponseServerError::class))]
            ),
        ]
    )
    annotation class GetWine

    @Target(AnnotationTarget.FUNCTION)
    @Retention(AnnotationRetention.RUNTIME)
    @Operation(
        summary = "와인 목록 조회",
        description = "와인 목록을 조회합니다.",
        responses = [
            SwaggerApiResponse(
                responseCode = "200",
                description = "와인 목록 조회 성공",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = GetWines200::class))]
            ),
            SwaggerApiResponse(
                responseCode = "500",
                description = "서버 에러",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = ApiResponseServerError::class))]
            ),
        ]
    )
    annotation class GetWines

    @Target(AnnotationTarget.FUNCTION)
    @Retention(AnnotationRetention.RUNTIME)
    @Operation(
        summary = "와인 생성",
        description = "와인을 생성합니다.",
        requestBody = RequestBody(
            description = "와인 생성 요청 데이터",
            required = true,
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = WineCreateRequestDto::class)
                )
            ]
        ),
        responses = [
            SwaggerApiResponse(
                responseCode = "201",
                description = "와인 생성 성공 후 생성된 와인 정보 반환",
                headers = [Header(name = "Location", description = "URL of the newly created wine resource", schema = Schema(type = "string"))],
                content = [Content(mediaType = "application/json", schema = Schema(implementation = CreateWine201::class))]
            ),
            SwaggerApiResponse(
                responseCode = "400",
                description = """
                    잘못된 요청. 다음과 같은 경우 발생할 수 있습니다.   
                    - 지역을 찾을 수 없음 : Invalid region id : {id}
                    - 카테고리를 찾을 수 없음 : Invalid category ids : {ids}
                    - 잘못된 와인 이름 : Name must be between 2 and 100 characters long
                    - 잘못된 와인 설명 : Description must be at most 500 characters long 
                    - 잘못된 지역 ID : RegionId must be a positive number
                    - 잘못된 알콜 도수 : Alcohol percentage must be greater than 0 또는 Alcohol percentage must be less than or equal to 100
                    - 잘못된 카테고리 ID 목록 : At most 5 categories can be tagged
                """,
                content = [Content(mediaType = "application/json", schema = Schema(implementation = ApiResponseBadRequest::class))]
            ),
            SwaggerApiResponse(
                responseCode = "500",
                description = "서버 에러",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = ApiResponseServerError::class))]
            ),
        ]
    )
    annotation class CreateWine


    @Target(AnnotationTarget.FUNCTION)
    @Retention(AnnotationRetention.RUNTIME)
    @Operation(
        summary = "와인 수정",
        description = "와인 정보를 수정합니다.",
        requestBody = RequestBody(
            description = "와인 수정 요청 데이터(수정할 데이터만 담아서 요청.)",
            required = true,
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = WineUpdateRequestDto::class)
                )
            ]
        ),
        responses = [
            SwaggerApiResponse(
                responseCode = "200",
                description = "와인 수정 성공 후 생성된 와인 정보 반환",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = UpdateWine200::class))]
            ),
            SwaggerApiResponse(
                responseCode = "400",
                description = """
                    잘못된 요청. 다음과 같은 경우 발생할 수 있습니다.   
                    - 지역을 찾을 수 없음 : Invalid region id : {id}
                    - 카테고리를 찾을 수 없음 : Invalid category ids : {ids}
                    - 잘못된 와인 이름 :
                        - Name cannot be blank
                        - Name must be between 2 and 100 characters long
                    - 잘못된 와인 설명 :
                        - Description cannot be blank
                        - Description must be at most 500 characters long 
                    - 잘못된 지역 ID : RegionId must be a positive number
                    - 잘못된 알콜 도수 : Alcohol percentage must be greater than 0 또는 Alcohol percentage must be less than or equal to 100
                    - 잘못된 카테고리 ID 목록 : At most 5 categories can be tagged
                """,
                content = [Content(mediaType = "application/json", schema = Schema(implementation = ApiResponseBadRequest::class))]
            ),
            SwaggerApiResponse(
                responseCode = "500",
                description = "서버 에러",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = ApiResponseServerError::class))]
            ),
        ]
    )
    annotation class UpdateWine

    @Target(AnnotationTarget.FUNCTION)
    @Retention(AnnotationRetention.RUNTIME)
    @Operation(
        summary = "와인 판매 목록 조회",
        description = "와인 판매 목록을 조회합니다.",
        responses = [
            SwaggerApiResponse(
                responseCode = "200",
                description = "와인 판매 목록 조회 성공",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = GetWineSales200::class))]
            ),
            SwaggerApiResponse(
                responseCode = "500",
                description = "서버 에러",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = ApiResponseServerError::class))]
            ),
        ]
    )
    annotation class GetWineSales

    @Target(AnnotationTarget.FUNCTION)
    @Retention(AnnotationRetention.RUNTIME)
    @Operation(
        summary = "와인 판매 생성",
        description = "와인 판매 생성합니다.",
        requestBody = RequestBody(
            description = "와인 판매 생성 요청 데이터",
            required = true,
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = WineCreateRequestDto::class)
                )
            ]
        ),
        responses = [
            SwaggerApiResponse(
                responseCode = "201",
                description = "와인 판매 생성 성공 후 생성된 와인 판매 정보 반환",
                headers = [Header(name = "Location", description = "URL of the newly created wine resource", schema = Schema(type = "string"))],
                content = [Content(mediaType = "application/json", schema = Schema(implementation = CreateWineSale201::class))]
            ),
            SwaggerApiResponse(
                responseCode = "400",
                description = """                
                """,
                content = [Content(mediaType = "application/json", schema = Schema(implementation = ApiResponseBadRequest::class))]
            ),
            SwaggerApiResponse(
                responseCode = "500",
                description = "서버 에러",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = ApiResponseServerError::class))]
            ),
        ]
    )
    annotation class CreateWineSale

    @Target(AnnotationTarget.FUNCTION)
    @Retention(AnnotationRetention.RUNTIME)
    @Operation(
        summary = "와인 판매 수정",
        description = "와인 판매 정보를 수정합니다.",
        requestBody = RequestBody(
            description = "와인 판매 수정 요청 데이터(수정할 데이터만 담아서 요청.)",
            required = true,
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = WineUpdateRequestDto::class)
                )
            ]
        ),
        responses = [
            SwaggerApiResponse(
                responseCode = "200",
                description = "와인 판매 수정 성공 후 생성된 와인 판매 정보 반환",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = UpdateWineSale200::class))]
            ),
            SwaggerApiResponse(
                responseCode = "400",
                description = """
                """,
                content = [Content(mediaType = "application/json", schema = Schema(implementation = ApiResponseBadRequest::class))]
            ),
            SwaggerApiResponse(
                responseCode = "500",
                description = "서버 에러",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = ApiResponseServerError::class))]
            ),
        ]
    )
    annotation class UpdateWineSale
}