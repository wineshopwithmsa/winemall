package org.wine.orderservice.order.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.wine.orderservice.order.dto.request.OrderPriceRequestDto
import org.wine.orderservice.order.dto.response.OrderPriceResponseDto
import org.wine.orderservice.order.entity.Coupon
import org.wine.orderservice.order.entity.WineSale
import org.wine.orderservice.order.repository.CouponRepository
import org.wine.orderservice.order.repository.OrderRepository
import org.wine.orderservice.order.repository.WineRepository

@Service
class OrderService  @Autowired constructor(
    private val orderRepository: OrderRepository,
    private val wineRepository: WineRepository,
    private val couponRepository: CouponRepository){

    fun getOrderPrice(orderPriceRequestDto: OrderPriceRequestDto) : OrderPriceResponseDto{
        //와인 가격 총합 금액
        val wines : List<WineSale> = wineRepository.findAllById(orderPriceRequestDto.productIdList)
        val sumPrice = wines.stream()
            .mapToInt(WineSale::price)
            .sum()

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
            sumPrice = sumPrice,
            finalPrice = finalPrice
        )
    }
}