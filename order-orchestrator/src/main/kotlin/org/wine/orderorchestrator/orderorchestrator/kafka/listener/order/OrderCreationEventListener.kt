package org.wine.orderorchestrator.orderorchestrator.kafka.listener.order

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.core.annotation.Order
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.listener.AcknowledgingMessageListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component
import org.wine.orderorchestrator.orderorchestrator.transcation.TransactionEventPublisher
import org.wine.orderorchestrator.orderorchestrator.transcation.event.OrderCreateEvent
import org.wine.orderorchestrator.orderorchestrator.transcation.saga.OrderSaga
import org.wine.orderorchestrator.orderorchestrator.transcation.topic.OrderTopic


@Component
class OrderCreationEventListener(
    private val eventPublisher: TransactionEventPublisher,
    private val objectMapper: ObjectMapper
): AcknowledgingMessageListener<String, String> {

    @KafkaListener(topics = [OrderTopic.ORDER_CREATED])
    override fun onMessage(data: ConsumerRecord<String, String>, acknowledgment: Acknowledgment?) {
        println(data.value())
        /*
        val (key, event) = data.key() to objectMapper.readValue(data.value(), OrderCreateEvent::class.java)
        println("Topic: $OrderTopic.ORDER_CREATED, key = $key, event: $event")

        val orderSaga = OrderSaga.init(eventPublisher, key, event)
        acknowledgment?.acknowledge()

         */
    }
}