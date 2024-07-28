package org.wine.orderservice.order.service

import ApiResponse
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import kotlinx.coroutines.reactive.awaitSingle
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.ParameterizedTypeReference
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.reactive.function.BodyInserter
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBodyOrNull
import org.springframework.web.util.UriComponentsBuilder
import org.wine.orderservice.common.kafka.OrderTopic
import org.wine.orderservice.order.dto.request.OrderPriceRequestDto
import org.wine.orderservice.order.dto.request.OrderRequestDto
import org.wine.orderservice.order.repository.OrderRepository
import org.wine.orderservice.common.kafka.publisher.TransactionEventPublisher
import org.wine.orderservice.common.kafka.event.OrderCreateEvent
import org.wine.orderservice.order.WineSaleDto
import org.wine.orderservice.order.dto.CouponDto
import org.wine.orderservice.order.dto.DiscountType
import org.wine.orderservice.order.dto.OrderDto
import org.wine.orderservice.order.dto.Response
import org.wine.orderservice.order.dto.response.OrderPriceResponseDto
import org.wine.orderservice.order.entity.Order
import reactor.core.publisher.Mono
import java.util.*

@Service
class OrderService  @Autowired constructor(
    private val orderRepository: OrderRepository,
    private val transactionEventPublisher: TransactionEventPublisher,
    private val webClient: WebClient
){
    private val logger = LoggerFactory.getLogger(javaClass)

    @Value("\${uri.product-service}")
    lateinit var productService: String

    @Value("\${uri.coupon-service}")
    lateinit var couponService: String


    //와인 가격 총합 금액
    suspend fun calculatePrice(orderPriceRequestDto: OrderPriceRequestDto): Int{
        val wineSaleIdsParam = orderPriceRequestDto.productList.map { it.wineSaleId }.joinToString(",")
        val wineSaleWebClientResponse = webClient.get()
            .uri("$productService/api/wine-sales/v1?ids=$wineSaleIdsParam")
            .retrieve()
            .awaitBodyOrNull<Response<List<WineSaleDto>>>() ?: throw Exception("Received null response")


        val wines : List<WineSaleDto> = wineSaleWebClientResponse.data
        var sumPrice : Int = 0

        orderPriceRequestDto
            .productList
            .forEach{
                val wineSaleId = it.wineSaleId
                var winePrice = 0

                wines.forEach{
                    if(it.wineSaleId == wineSaleId){
                        winePrice = it.price
                    }
                }

                sumPrice += winePrice * it.quantity
            }

        return sumPrice
    }


    //쿠폰 적용가
    suspend fun applyCoupon(originPrice : Int, couponId : Long): Int{

        var finalPrice : Int = originPrice

        val couponWebClientResponse = webClient.get()
            .uri(couponService + "/api/coupon/v1/"+couponId)
            .retrieve()
            .awaitBodyOrNull<Response<CouponDto>>() ?: throw Exception("Received null response")


        if(couponWebClientResponse.status == HttpStatus.OK.value()){ //쿠폰이 존재하면 쿠폰 적용가 계산
            val coupon : CouponDto = couponWebClientResponse.data

            if(coupon.discountType == DiscountType.AMOUNT){
                finalPrice = originPrice - coupon.discountValue.toInt()
            }
            else{
                finalPrice = originPrice * (100 - coupon.discountValue.toInt()) / 100
            }
        }


        return finalPrice
    }

    suspend fun getOrderPrice(orderPriceRequestDto: OrderPriceRequestDto): OrderPriceResponseDto{

        val sumPrice = calculatePrice(orderPriceRequestDto)
        val finalPrice = applyCoupon(sumPrice, orderPriceRequestDto.couponId)

        return OrderPriceResponseDto(
            sumPrice = sumPrice,
            finalPrice = finalPrice
        )
    }


    @Transactional
    suspend fun createOrder(orderRequestDto: OrderRequestDto, rsrvDate : String){
        var order : Order = orderRequestDto.toEntity(rsrvDate)

        orderRepository.save(
            order
        ).let{
                transactionEventPublisher.publishEvent(
                    topic = OrderTopic.ORDER_CREATED,
                    key = UUID.randomUUID().toString().replace("-", ""),
                    event = OrderCreateEvent(
                        orderId = order.orderId,
                        wineOrderList = orderRequestDto.wineList,
                        couponId = orderRequestDto.couponId,
                        memberId = orderRequestDto.memberId
                    )
                ).then(Mono.just(Unit))
                    .also{println("트랜잭션 요청 topic: ORDER_CREATED, orderId = ${order.orderId}")}
            }
            .awaitSingle()
    }

    @Transactional
    fun approveOrder(id: Long) {
        orderRepository.findByIdOrNull(id)
            ?.approve()
    }

    @Transactional
    fun cancelOrder(id: Long) {
        orderRepository.findByIdOrNull(id)
            ?.cancel()
    }
}