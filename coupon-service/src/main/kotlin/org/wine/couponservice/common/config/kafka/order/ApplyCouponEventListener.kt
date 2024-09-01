//package org.wine.couponservice.common.config.kafka.order
//
//import com.fasterxml.jackson.databind.ObjectMapper
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.reactive.awaitFirstOrNull
//import org.apache.kafka.clients.consumer.ConsumerRecord
//import org.slf4j.LoggerFactory
//import org.springframework.kafka.annotation.KafkaListener
//import org.springframework.kafka.listener.AcknowledgingMessageListener
//import org.springframework.kafka.support.Acknowledgment
//import org.springframework.stereotype.Component
//import org.wine.userservice.common.config.boundedElasticScope
//import org.wine.userservice.common.kafka.OrderTopic
//import org.wine.userservice.common.kafka.event.ApplyCouponCompletedEvent
//import org.wine.userservice.common.kafka.event.ApplyCouponEvent
//import org.wine.userservice.common.kafka.event.ApplyCouponFailedEvent
//import org.wine.userservice.common.kafka.publisher.TransactionEventPublisher
//import org.wine.userservice.membercoupon.service.MemberCouponService
//
//@Component
//class ApplyCouponEventListener(
//    private val objectMapper: ObjectMapper,
//    private val transactionEventPublisher: TransactionEventPublisher,
//    private val memberCouponService: MemberCouponService
//): AcknowledgingMessageListener<String, String> {
//
//    private val logger = LoggerFactory.getLogger(javaClass)
//    @KafkaListener(topics = [OrderTopic.APPLY_COUPON], groupId = "order")
//    override fun onMessage(data: ConsumerRecord<String, String>, acknowledgment: Acknowledgment?) {
//        val (key, event) = data.key() to objectMapper.readValue(data.value(), ApplyCouponEvent::class.java)
//        logger.info("Topic: ${OrderTopic.APPLY_COUPON}, key = $key, event: $event")
//        try{
//            memberCouponService.useCouponMember(event.couponId)
//
//            transactionEventPublisher.publishEvent(
//                topic = OrderTopic.APPLY_COUPON_COMPLETED,
//                key = key,
//                event = ApplyCouponCompletedEvent(event.orderId)
//            )
//        }
//        catch (e: Exception){
//            logger.info("Check Stock Failed, error: "+ e.message)
//
//            transactionEventPublisher.publishEvent(
//                topic = OrderTopic.APPLY_COUPON_FAILED,
//                key = key,
//                event = ApplyCouponFailedEvent(event.orderId, e.message!!)
//            )
//        }.let{
//            boundedElasticScope.launch {
//                it.awaitFirstOrNull()
//            }
//        }
//
//        acknowledgment?.acknowledge()
//    }
//}