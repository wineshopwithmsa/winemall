package org.wine.productservice.product.controller

import ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.wine.productservice.config.WineApiSpec
import org.wine.productservice.wine.dto.WineSaleCreateRequestDto
import org.wine.productservice.wine.dto.WineSaleResponse
import org.wine.productservice.wine.dto.WineSaleUpdateRequestDto
import org.wine.productservice.wine.dto.WineSalesRequestDto
import org.wine.productservice.wine.service.WineSaleService
import java.net.URI

@RestController
@RequestMapping("/api/wine-sales")
@Tag(name = "WineSale", description = "와인 판매 API")
class WineSaleController @Autowired constructor(
    private val wineSaleService: WineSaleService,
){
    @GetMapping("/v1")
    @WineApiSpec.GetWineSales
    suspend fun getWineSales(@ModelAttribute requestDto: WineSalesRequestDto): ApiResponse<Any> {
        var wines = wineSaleService.getWineSales(requestDto)

        return ApiResponse.Success(
            status = HttpStatus.OK.value(),
            message = "Success",
            data = wines
        )
    }

//    @GetMapping("/v1/{wineSaleId}")
//    @WineApiSpec.GetWineSale
//    fun getWineSales(@ModelAttribute requestDto: WineSalesRequestDto): ApiResponse<Any> {
//        var wines = wineSaleService.getWineSales(requestDto)
//
//        return ApiResponse.Success(
//            status = HttpStatus.OK.value(),
//            message = "Success",
//            data = wines
//        )
//    }


    @PostMapping("/v1")
    @WineApiSpec.CreateWineSale
    suspend fun createWineSale(@Valid @RequestBody requestDto: WineSaleCreateRequestDto,
                       @RequestHeader headers: HttpHeaders
    ): ResponseEntity<ApiResponse<Any>> {
        val wineSale = wineSaleService.addWineSale(requestDto, headers)
        val location = URI.create("/api/wine-sales/v1/${wineSale.id}")
        return ResponseEntity.created(location)
            .body(ApiResponse.Success(
                status = HttpStatus.CREATED.value(),
                message = "Created",
                data = WineSaleResponse(wineSale)
            ))
    }

    @PatchMapping("/v1/{wineSaleId}")
    @WineApiSpec.UpdateWineSale
    suspend fun updateWineSale(@Valid @PathVariable wineSaleId: Long, @RequestBody requestDto: WineSaleUpdateRequestDto): ApiResponse<Any> {
        val wineSale = wineSaleService.updateWineSale(wineSaleId, requestDto)
        return ApiResponse.Success(
            status = HttpStatus.OK.value(),
            message = "Success",
            data = mapOf("wine" to wineSale)
        )
    }
}