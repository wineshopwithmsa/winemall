package org.wine.orderservice.order.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.wine.orderservice.order.dto.request.OrderPriceRequestDto
import org.wine.orderservice.order.dto.request.OrderRequestDto
import org.wine.orderservice.order.repository.OrderRepository
import org.wine.orderservice.kafka.publisher.TransactionEventPublisher
import org.wine.orderservice.kafka.event.OrderCreateEvent
import org.wine.orderservice.order.WineSaleDto
import org.wine.orderservice.order.dto.CouponDto
import org.wine.orderservice.order.dto.DiscountType
import org.wine.orderservice.order.dto.OrderDto
import org.wine.orderservice.order.dto.response.OrderPriceResponseDto
import org.wine.orderservice.order.entity.Order
import org.wine.orderservice.order.feign.CouponServiceFeign
import org.wine.orderservice.order.feign.ProductServiceFeign
import reactor.core.publisher.Mono
import java.util.*

@Service
class OrderService  @Autowired constructor(
    private val orderRepository: OrderRepository,
    private val transactionEventPublisher: TransactionEventPublisher,
    private val productServiceFeign: ProductServiceFeign,
    private val couponServiceFeign: CouponServiceFeign
){
    private val logger = LoggerFactory.getLogger(javaClass)


    //와인 가격 총합 금액
    fun calculatePrice(orderPriceRequestDto: OrderPriceRequestDto): Int{
        val wines : List<WineSaleDto> = jacksonObjectMapper()
            .readValue<List<WineSaleDto>>(productServiceFeign
                .getWineSale(orderPriceRequestDto.productList)
                .response.toString())


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
    fun applyCoupon(originPrice : Int, couponId : Long): Int{

        var finalPrice : Int = 0

        val coupon : CouponDto = jacksonObjectMapper()
            .readValue<CouponDto>(couponServiceFeign
                .getCouponInfo(couponId.toString())
                .response.toString())

        if(coupon.discountType == DiscountType.AMOUNT){
            finalPrice = originPrice - coupon.discountValue.toInt()
        }
        else{
            finalPrice = originPrice * (100 - coupon.discountValue.toInt()) / 100
        }

        return finalPrice
    }

    fun getOrderPrice(orderPriceRequestDto: OrderPriceRequestDto): OrderPriceResponseDto{

        val sumPrice = calculatePrice(orderPriceRequestDto)
        val finalPrice = applyCoupon(sumPrice, orderPriceRequestDto.couponID)

        return OrderPriceResponseDto(
            sumPrice = sumPrice,
            finalPrice = finalPrice
        )
    }


    fun createOrder(orderRequestDto: OrderRequestDto){
        var order : Order = orderRequestDto.toEntity()

        orderRepository.save(
            order
        ).let{
                transactionEventPublisher.publishEvent(
                    topic = "ORDER-CREATED",
                    key = UUID.randomUUID().toString().replace("-", ""),
                    event = OrderCreateEvent(
                        orderId = order.orderId,
                        wineList = orderRequestDto.wineList,
                        couponId = orderRequestDto.couponId,
                        memberId = orderRequestDto.memberId
                    )
                ).then(Mono.just(Unit))
                    .also{logger.info("트랜잭션 요청 topic: ORDER_CREATED, orderId = ${order.orderId}")}
                    .subscribe()
            }
    }
}