package org.wine.orderservice.order.service

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.wine.orderservice.Auth.service.AuthService
import org.wine.orderservice.common.exception.NoValueException
import org.wine.orderservice.common.kafka.OrderTopic
import org.wine.orderservice.common.kafka.event.OrderCreateEvent
import org.wine.orderservice.common.kafka.publisher.TransactionEventPublisher
import org.wine.orderservice.order.dto.CouponDto
import org.wine.orderservice.order.dto.DiscountType
import org.wine.orderservice.order.dto.OrderDto
import org.wine.orderservice.order.dto.request.OrderPriceRequestDto
import org.wine.orderservice.order.dto.request.OrderRequestDto
import org.wine.orderservice.order.dto.response.OrderPriceResponseDto
import org.wine.orderservice.order.entity.Order
import org.wine.orderservice.order.entity.OrderDetail
import org.wine.orderservice.order.mapper.OrderDetailMapper
import org.wine.orderservice.order.mapper.OrderMapper
import org.wine.orderservice.order.repository.OrderDetailRepository
import org.wine.orderservice.order.repository.OrderRepository
import org.wine.orderservice.order.webClient.CouponWebClient
import org.wine.orderservice.order.webClient.ProductWebClient
import reactor.core.publisher.Mono
import java.util.*

@Service
class OrderService  @Autowired constructor(
    private val orderRepository: OrderRepository,
    private val orderDetailRepository: OrderDetailRepository,

    private val orderMapper: OrderMapper,
    private val orderDetailMapper: OrderDetailMapper,

    private val transactionEventPublisher: TransactionEventPublisher,
    private val couponWebClient: CouponWebClient,
    private val productWebClient: ProductWebClient,

    private val authService: AuthService,

){
    private val logger = LoggerFactory.getLogger(javaClass)


    //와인 가격 총합 금액
    suspend fun calculatePrice(orderPriceRequestDto: OrderPriceRequestDto): Int{
        val wineSaleIdsParam = orderPriceRequestDto.productList.map { it.wineSaleId }.joinToString(",")
        val wines = productWebClient.getWineSaleInfo(wineSaleIdsParam).data

        if(wines.isEmpty()){
            throw NoValueException("No Wine Sale info error")
        }

        val priceMap = wines.associateBy({it.wineSaleId}, {it.price})
        return orderPriceRequestDto
            .productList
            .sumOf{ it ->
                (priceMap[it.wineSaleId] ?: 0 )* it.quantity
            }
    }


    //쿠폰 적용가
    suspend fun applyCoupon(originPrice : Int, couponId : Long?): Int{
        if(couponId == null) return originPrice

        val couponWebClientResponse = couponWebClient.getCoupon(couponId.toString())

        if(couponWebClientResponse.status == HttpStatus.OK.value()){ //쿠폰이 존재하면 쿠폰 적용가 계산
            val coupon : CouponDto = couponWebClientResponse.data
            return calculateDiscountedPrice(originPrice, coupon)
        }

        return originPrice
    }

    fun calculateDiscountedPrice(originPrice: Int, coupon: CouponDto): Int{
        return when(coupon.discountType){
            DiscountType.AMOUNT-> {
                val discountedPrice = originPrice - coupon.discountValue.toInt()
                if (discountedPrice < 0) 0 else discountedPrice
            }
            DiscountType.PERCENTAGE ->{
                val discountedPrice = originPrice * (100 - coupon.discountValue.toInt()) / 100
                if (discountedPrice < 0) 0 else discountedPrice
            }
        }
    }

    suspend fun getOrderPrice(orderPriceRequestDto: OrderPriceRequestDto): OrderPriceResponseDto{
        val sumPrice = calculatePrice(orderPriceRequestDto)
        val finalPrice = applyCoupon(sumPrice, orderPriceRequestDto.couponId)

        return OrderPriceResponseDto(
            sumPrice = sumPrice,
            finalPrice = finalPrice
        )
    }

    suspend fun getOrderList(headers: HttpHeaders): List<OrderDto>? {
        val memberId = withContext(Dispatchers.IO) {
            authService.getMemberIdFromToken(headers)
        }

        val orderList = withContext(Dispatchers.IO) {
            orderRepository.findAllByMemberIdOrderByOrderIdDesc(memberId)
        }

        return if (orderList.isEmpty()) {
            null
        } else {
            orderList.map { orderMapper.toOrderDto(it) }
        }

    }


    suspend fun getOrderDetails(orderId: Long): OrderDto? {
        return withContext(Dispatchers.IO) {
            val order = orderRepository.findById(orderId)
            if (order.isPresent) {
                orderMapper.toOrderDto(order.get())
            } else {
                null
            }
        }
    }

    @Transactional
    suspend fun createOrder(orderRequestDto: OrderRequestDto, headers: HttpHeaders): OrderDto{
        val memberId = authService.getMemberIdFromToken(headers)
        val order : Order = orderRequestDto.toOrder(memberId, orderRequestDto)

        withContext(Dispatchers.IO) {
            orderRepository.save(order)
            val orderDetails : List<OrderDetail> = orderRequestDto.wineList
                .map{ orderDetailMapper.toOrderDetail(it, order) }
            orderDetailRepository.saveAll(orderDetails)
        }

        publishOrderCreatedEvent(order, orderRequestDto, memberId)

        return orderMapper.toOrderDto(order)
    }


    suspend fun publishOrderCreatedEvent(order: Order, orderRequestDto: OrderRequestDto, memberId: Long){
        transactionEventPublisher.publishEvent(
                topic = OrderTopic.ORDER_CREATED,
                key = UUID.randomUUID().toString().replace("-", ""),
                event = OrderCreateEvent(
                    orderId = order.orderId,
                    wineOrderList = orderRequestDto.wineList,
                    couponId = orderRequestDto.couponId,
                    memberId = memberId
                )
        ).awaitSingle()

        logger.info("트랜잭션 요청 topic: ORDER_CREATED, orderId = ${order.orderId}")
    }

    @Transactional
    fun approveOrder(id: Long) {
        orderRepository.findByIdOrNull(id)
            ?.approve()

        logger.info("트랜잭션 요청 topic: ORDER_APPROVED, orderId = ${id}")
    }

    @Transactional
    fun cancelOrder(id: Long) {
        orderRepository.findByIdOrNull(id)
            ?.cancel()

        logger.info("트랜잭션 요청 topic: ORDER_CANCELED, orderId = ${id}")
    }
}