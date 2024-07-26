package org.wine.productservice.product.controller

import ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.wine.productservice.config.WineApiSpec
import org.wine.productservice.wine.dto.WineSalesRequestDto
import org.wine.productservice.wine.service.WineSaleService

@RestController
@RequestMapping("/api/wine-sales")
@Tag(name = "WineSale", description = "와인 판매 API")
class WineSaleController @Autowired constructor(
    private val wineSaleService: WineSaleService,
){
    @GetMapping("/v1")
    @WineApiSpec.GetWineSales
    fun getWineSales(@ModelAttribute requestDto: WineSalesRequestDto): ApiResponse<Any> {
        var wines = wineSaleService.getWineSales(requestDto)

        return ApiResponse.Success(
            status = HttpStatus.OK.value(),
            message = "Success",
            data = wines
        )
    }

    //    @PostMapping("/v1")
//    @WineApiSpec.CreateWine
//    fun createWine(@Valid @RequestBody requestDto: WineSaleCreateRequestDto): ResponseEntity<ApiResponse<Any>> {
//        val wineSale = wineService.addWineSale(requestDto)
//        val location = URI.create("/api/wine-sales/v1/${wineSale.wineSaleId}")
//        return ResponseEntity.created(location)
//            .body(ApiResponse.Success(
//                status = HttpStatus.CREATED.value(),
//                message = "Created",
//                data = WineSaleResponse(wineSale)
//            ))
//    }

}