package org.wine.orderservice.order.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.wine.orderservice.order.dto.request.OrderPriceRequestDto
import org.wine.orderservice.order.dto.request.OrderRequestDto
import org.wine.orderservice.order.dto.response.OrderPriceResponseDto
import org.wine.orderservice.order.repository.OrderRepository
import org.wine.orderservice.kafka.publisher.TransactionEventPublisher
import org.wine.orderservice.kafka.event.OrderCreateEvent
import org.wine.orderservice.order.entity.Order
import reactor.core.publisher.Mono
import java.util.*

@Service
class OrderService  @Autowired constructor(
    private val orderRepository: OrderRepository,
    private val transactionEventPublisher: TransactionEventPublisher
){
    private val logger = LoggerFactory.getLogger(javaClass)
    /*
       fun getOrderPrice(orderPriceRequestDto: OrderPriceRequestDto) : OrderPriceResponseDto{

           //와인 가격 총합 금액
           var sumPrice : Int = 0;
           orderPriceRequestDto.productList
               .forEach { orderDto ->
                   sumPrice += wineRepository.findById(orderDto.productId)
                       .get().price * orderDto.quantity }


           //쿠폰 적용가
           var finalPrice : Int = sumPrice;

           if(!orderPriceRequestDto.couponIdList.isEmpty()){
               val coupons : List<Coupon> = couponRepository.findAllById(orderPriceRequestDto.couponIdList)
               val sumCoupon = coupons.stream()
                       .mapToInt(Coupon::discountValue)
                       .sum()

               finalPrice = sumPrice * sumPrice
           }

        return OrderPriceResponseDto(
            sumPrice = 1,
            finalPrice = 1
        )
    }
*/

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