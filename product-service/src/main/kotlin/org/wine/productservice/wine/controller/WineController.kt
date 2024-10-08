package org.wine.productservice.product.controller

import ApiResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam
import org.wine.productservice.config.WineApiSpec
import org.wine.productservice.shared.validator.ValidIds
import org.wine.productservice.wine.dto.*
import org.wine.productservice.wine.service.WineService
import java.net.URI
import java.util.concurrent.atomic.AtomicInteger

@RestController
@RequestMapping("/api/wines")
@Tag(name = "Wine", description = "와인 API")
class WineController @Autowired constructor(
    private val wineService: WineService,
){
    private val logger = LoggerFactory.getLogger(javaClass)
    private val counter = AtomicInteger(0)

    @GetMapping("/health")
    @Operation(summary = "Health Check", description = "서버 상태 확인")
    suspend fun health(): String {
        return "ok"
    }

    @PostMapping("/v1")
    @WineApiSpec.CreateWine
    suspend fun createWine(@Valid @RequestBody requestDto: WineCreateRequestDto,
                   @RequestHeader headers: HttpHeaders): ResponseEntity<ApiResponse<Any>> {
        val wine = wineService.addWine(requestDto, headers)
        val location = URI.create("/api/wines/v1/${wine.id}")  // 새로 생성된 와인의 URI를 생성합니다.
        return ResponseEntity.created(location)
            .body(ApiResponse.Success(
                status = HttpStatus.CREATED.value(),
                message = "Created",
                data = WineResponse(wine)
            ))
    }

    @Validated
    @GetMapping("/v1")
    @WineApiSpec.GetWines
    suspend fun getWines(
        @Parameter(description = "페이지 번호", example = "1")
        @RequestParam(defaultValue = "1") @Min(1) page: Int,

        @Parameter(description = "페이지 당 아이템 수", example = "3")
        @RequestParam(defaultValue = "10") @Min(1) @Max(100) perPage: Int,

        @Parameter(description = "지역 ID(복수 가능. 쉼표로 구분)", example = "1,2,3")
        @ValidIds(fieldName = "regionId") @RequestParam(required = false) regionId: String?,

        @Parameter(description = "카테고리 ID(복수 가능. 쉼표로 구분)", example = "1,2,3")
        @ValidIds(fieldName = "categoryId") @RequestParam(required = false) categoryId: String?,
        ): ApiResponse<Any> {
        val regionIds = regionId?.split(",")?.map { it.toLong() }
        val categoryIds = categoryId?.split(",")?.map { it.toLong() }

        val wines: PaginatedWineResponseDto = wineService.getWines(
            page = page,
            perPage = perPage,
            regionIds = regionIds,
            categoryIds = categoryIds
        )

        return ApiResponse.Success(
            status = HttpStatus.OK.value(),
            message = "Success",
            data = WinesResponse(wines)
        )
    }

    @GetMapping("/v1/{wineId}")
    @WineApiSpec.GetWine
    suspend fun getWine(@PathVariable wineId: Long): ApiResponse<Any>{
        val requestId = counter.incrementAndGet()
        logger.debug("Request $requestId: Started processing getWine for id $wineId on thread ${Thread.currentThread().name}")

        val wine = wineService.getWine(wineId)  // wineService.getWine이 suspend 함수라고 가정
        logger.debug("Request $requestId: Finished processing getWine for id $wineId on thread ${Thread.currentThread().name}")

        return ApiResponse.Success(
            status = HttpStatus.OK.value(),
            message = "Success",
            data = WineResponse(wine)
        )
    }

    @PatchMapping("/v1/{wineId}")
    @WineApiSpec.UpdateWine
    suspend fun updateWine(@Valid @PathVariable wineId: Long, @RequestBody requestDto: WineUpdateRequestDto): ApiResponse<Any> {
        val wine = wineService.updateWine(wineId, requestDto)
        return ApiResponse.Success(
            status = HttpStatus.OK.value(),
            message = "Success",
            data = mapOf("wine" to wine)
        )
    }
}