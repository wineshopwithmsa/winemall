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
import org.wine.orderorchestrator.orderorchestrator.order.repository.SagaRepository
import org.wine.orderorchestrator.orderorchestrator.order.transcation.TransactionEventPublisher
import org.wine.orderorchestrator.orderorchestrator.order.transcation.event.OrderCreateEvent
import org.wine.orderorchestrator.orderorchestrator.order.transcation.event.OrderFailedEvent
import org.wine.orderorchestrator.orderorchestrator.order.transcation.saga.OrderSaga
import org.wine.orderorchestrator.orderorchestrator.order.transcation.state.OrderFailed
import org.wine.orderorchestrator.orderorchestrator.order.transcation.topic.OrderTopic

@Component
@Slf4j
class OrderFailedEventListener(
    private val eventPublisher: TransactionEventPublisher,
    private val sagaRepository: SagaRepository,
    private val objectMapper: ObjectMapper
): AcknowledgingMessageListener<String, String> {

    private val logger = LoggerFactory.getLogger(javaClass)
    @KafkaListener(topics = [OrderTopic.ORDER_ROLLBACK], groupId = "order-orchestrator")
    override fun onMessage(data: ConsumerRecord<String, String>, acknowledgment: Acknowledgment?) {
        val (key, event) = data.key() to objectMapper.readValue(data.value(), OrderFailedEvent::class.java)
        logger.info("Topic: $OrderTopic.ORDER_ROLLBACK, key = $key, event: $event")

        sagaRepository.findById(key)?.let{
            boundedElasticScope.launch {
                it.changeStateAndOperate(
                    OrderFailed()
                )
            }
            sagaRepository.deleteById(key)
        }


        acknowledgment?.acknowledge()

    }
}