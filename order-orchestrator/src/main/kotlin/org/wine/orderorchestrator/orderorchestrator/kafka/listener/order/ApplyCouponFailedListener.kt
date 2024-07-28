package org.wine.orderorchestrator.orderorchestrator.kafka.listener.order

import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.launch
import lombok.extern.slf4j.Slf4j
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.listener.AcknowledgingMessageListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component
import org.wine.orderorchestrator.orderorchestrator.coroutine.boundedElasticScope
import org.wine.orderorchestrator.orderorchestrator.order.transcation.TransactionEventPublisher
import org.wine.orderorchestrator.orderorchestrator.order.transcation.event.OrderCreateEvent
import org.wine.orderorchestrator.orderorchestrator.order.transcation.saga.OrderSaga
import org.wine.orderorchestrator.orderorchestrator.order.transcation.state.CouponApplied
import org.wine.orderorchestrator.orderorchestrator.order.transcation.state.CouponApplyFailed
import org.wine.orderorchestrator.orderorchestrator.order.transcation.state.StockCheckFailed
import org.wine.orderorchestrator.orderorchestrator.order.transcation.topic.OrderTopic

@Component
@Slf4j
class ApplyCouponFailedListener(
    private val eventPublisher: TransactionEventPublisher,
    private val objectMapper: ObjectMapper
): AcknowledgingMessageListener<String, String> {

    private val logger = LoggerFactory.getLogger(javaClass)
    @KafkaListener(topics = [OrderTopic.APPLY_COUPON_FAILED], groupId = "order-orchestrator")
    override fun onMessage(data: ConsumerRecord<String, String>, acknowledgment: Acknowledgment?) {
        val (key, event) = data.key() to objectMapper.readValue(data.value(), OrderCreateEvent::class.java)
        logger.info("Topic: ${OrderTopic.APPLY_COUPON_FAILED}, key = $key, event: $event")

        val orderSaga = OrderSaga.init(eventPublisher, key, event, CouponApplyFailed())

        boundedElasticScope.launch {
            orderSaga.operate()
        }

        acknowledgment?.acknowledge()
    }
}