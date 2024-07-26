package org.wine.orderservice.order.controller

import ApiResponse
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitBodyOrNull
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.wine.orderservice.order.dto.request.OrderPriceRequestDto
import org.wine.orderservice.order.dto.request.OrderRequestDto
import org.wine.orderservice.order.dto.response.OrderPriceResponseDto
import org.wine.orderservice.order.service.OrderService

@RestController
@RequestMapping("/api/order")
class OrderController @Autowired constructor(
    private val orderService: OrderService){
    @GetMapping("/v1/health")
    fun getUsers(): ApiResponse<Any> {
        return ApiResponse.Success(data = "test data")
    }

    @PostMapping("/v1")
    @ApiOperation(value = "주문")
    @ApiImplicitParams(value = [
        ApiImplicitParam(name ="receiverPhoneNumber", value = "수령자 핸드폰번호", dataType = "String", required = true),
        ApiImplicitParam(name = "receiverName", value = "수령자 이름", dataType = "String", required = true),
        ApiImplicitParam(name ="receiverAddr", value = "수령자 주소", dataType = "String", required = true),
        ApiImplicitParam(name = "receiverAddrDetail", value = "수령자 상세주소", dataType = "String", required = true),
        ApiImplicitParam(name ="totalPrice", value = "주문 상품 금액 합", dataType = "Int", required = true),
        ApiImplicitParam(name = "finalPrice", value = "할인 적용 금액", dataType = "Int", required = true),
        ApiImplicitParam(name ="rsrvDate", value = "예약일", dataType = "String", required = false),
        ApiImplicitParam(name = "wineList", value = "구매할 와인리스트", dataType = "List<OrderDto>", required = true),
        ApiImplicitParam(name ="couponId", value = "적용할 쿠폰 ID", dataType = "Long", required = true),
        ApiImplicitParam(name = "memberId", value = "고객 ID", dataType = "Long", required = true),
    ])
    @ApiResponses(value = [
        io.swagger.annotations.ApiResponse(code=200, message = "성공")
    ])
    suspend fun orderWine(@RequestBody orderRequestDto: OrderRequestDto, @RequestParam(required = false, defaultValue = "") rsrvDate: String) :ApiResponse<Any>{
        orderService.createOrder(orderRequestDto, rsrvDate)

        return ApiResponse.Success(HttpStatus.OK.value(), message = "success", data= null)
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
    suspend fun getPrice(@RequestBody orderPriceDto: OrderPriceRequestDto): ApiResponse<Any> {
        val orderPrice : OrderPriceResponseDto = orderService.getOrderPrice(orderPriceDto)

        return ApiResponse.Success(
                status = HttpStatus.OK.value(), message = "Success", data = orderPrice)

    }

}