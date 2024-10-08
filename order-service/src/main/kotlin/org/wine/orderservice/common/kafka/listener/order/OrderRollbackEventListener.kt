package org.wine.orderservice.common.kafka.listener.order

import com.fasterxml.jackson.databind.ObjectMapper
import lombok.extern.slf4j.Slf4j
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.listener.AcknowledgingMessageListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component
import org.wine.orderservice.common.kafka.OrderTopic
import org.wine.orderservice.common.kafka.event.OrderCompletedEvent
import org.wine.orderservice.common.kafka.event.OrderFailedEvent
import org.wine.orderservice.order.service.OrderService

@Component
class OrderRollbackEventListener (
    private val orderService: OrderService,
    private val objectMapper: ObjectMapper
): AcknowledgingMessageListener<String, String> {
    private val logger = LoggerFactory.getLogger(javaClass)

    @KafkaListener(topics = [OrderTopic.ORDER_FAILED], groupId = "order")
    override fun onMessage(data: ConsumerRecord<String, String>, acknowledgment: Acknowledgment?) {
        val (key, event) = data.key() to objectMapper.readValue(data.value(), OrderFailedEvent::class.java)
        logger.info("Topic: ${OrderTopic.ORDER_FAILED}, key = $key, event: $event")

        orderService.cancelOrder(event.orderId)
        acknowledgment?.acknowledge()

    }
}