package org.wine.orderservice.order.controller

import ApiResponse
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.wine.orderservice.order.dto.request.OrderPriceRequestDto
import org.wine.orderservice.order.dto.response.OrderPriceResponseDto
import org.wine.orderservice.order.service.OrderService

@RestController
@RequestMapping("/api/order")
class OrderController @Autowired constructor(
    private val orderService: OrderService){
    @GetMapping("/v1")
    fun getUsers(): ApiResponse<Any> {
        return ApiResponse.Success(data = "test data")
    }

    @GetMapping("/price")
    @ApiOperation(value = "주문총금액, 쿠폰 적용가 조회")
    @ApiImplicitParams(value = [
        ApiImplicitParam(name ="productIdList", value = "구매하고자하는 상품의 ID list(필수)", dataType = "List<Long>", required = true),
        ApiImplicitParam(name = "couponIdList", value = "적용하고자하는 쿠폰의 ID list", dataType = "List<Long>", required = false)
    ])
    @ApiResponses(value = [
        io.swagger.annotations.ApiResponse(code=200, message = "성공")
    ])
    fun getPrice(@RequestBody orderPriceDto: OrderPriceRequestDto): ApiResponse<Any> {
        val orderPrice : OrderPriceResponseDto = orderService.getOrderPrice(orderPriceDto)

        return ApiResponse.Success(
                status = HttpStatus.OK.value(), message = "Success", data = orderPrice)

    }
}