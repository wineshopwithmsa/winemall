package org.wine.productservice.product.controller

import ApiResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.wine.productservice.config.WineApiSpec
import org.wine.productservice.wine.dto.*
import org.wine.productservice.wine.service.WineService

@RestController
@RequestMapping("/api/wines")
@Tag(name = "Wine", description = "와인 API")
class WineController @Autowired constructor(
    private val wineService: WineService,
){
    @GetMapping("/health")
    @Operation(summary = "Health Check", description = "서버 상태 확인")
    fun health(): String {
        return "ok";
    }

    @PostMapping("/v1")
    @WineApiSpec.CreateWine
    fun createWine(@Valid @RequestBody requestDto: WineCreateRequestDto): ResponseEntity<ApiResponse<Any>> {
        val wine = wineService.addWine(requestDto)
        return ResponseEntity.ok(ApiResponse.Success(
            status = 200,
            message = "Success",
            data = WineResponse(wine)
        ))
    }

    @Validated
    @GetMapping("/v1")
    @WineApiSpec.GetWines
    fun getWines(
        @RequestParam(defaultValue = "1") @Min(1) page: Int,
        @RequestParam(defaultValue = "3") @Min(1) @Max(100) perPage: Int,
    ): ResponseEntity<ApiResponse<Any>> {
        val wines: PaginatedWineResponseDto = wineService.getWines(page, perPage)
        return ResponseEntity.ok(ApiResponse.Success(
            status = 200,
            message = "Success",
            data = WinesResponse(wines)
        ))
    }

    @GetMapping("/v1/{wineId}")
    @WineApiSpec.GetWine
    fun getWine(@PathVariable wineId:Long): ResponseEntity<ApiResponse<Any>> {
        var wine = wineService.getWine(wineId)
        return ResponseEntity.ok(ApiResponse.Success(
            status = HttpStatus.OK.value(),
            message = "Success",
            data = WineResponse(wine)
        ))
    }

    @PatchMapping("/v1/{wineId}")
    @WineApiSpec.UpdateWine
    fun updateWine(@Valid @PathVariable wineId: Long, @RequestBody requestDto: WineUpdateRequestDto): ResponseEntity<ApiResponse<Any>> {
        val wine = wineService.updateWine(wineId, requestDto)
        return ResponseEntity.ok(ApiResponse.Success(
            status = HttpStatus.OK.value(),
            message = "Success",
            data = mapOf("wine" to wine)
        ))
    }
}