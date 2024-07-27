package org.wine.productservice.kafka.listener

import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.launch
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.listener.AcknowledgingMessageListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component
import org.wine.productservice.config.boundedElasticScope
import org.wine.productservice.kafka.OrderTopic
import org.wine.productservice.kafka.event.OrderRollbackEvent
import org.wine.productservice.kafka.event.StockRollbackEvent
import org.wine.productservice.kafka.publisher.TransactionEventPublisher
import org.wine.productservice.wine.service.WineSaleService

@Component
class StockRollbackEventListener(
    private val objectMapper: ObjectMapper,
    private val transactionEventPublisher: TransactionEventPublisher,
    private val wineSaleService: WineSaleService
): AcknowledgingMessageListener<String, String> {

    private val logger = LoggerFactory.getLogger(javaClass)
    @KafkaListener(topics = [OrderTopic.STOCK_ROLLBACK], groupId = "order")
    override fun onMessage(data: ConsumerRecord<String, String>, acknowledgment: Acknowledgment?) {
        val (key, event) = data.key() to objectMapper.readValue(data.value(), StockRollbackEvent::class.java)
        logger.info("Topic: ${OrderTopic.STOCK_ROLLBACK}, key = $key, event: $event")

        wineSaleService.incrementStock(event)

        boundedElasticScope.launch {
            transactionEventPublisher.publishEvent(
                topic = OrderTopic.ORDER_ROLLBACK,
                key = key,
                event = OrderRollbackEvent(event.orderId)
            ).awaitFirstOrNull()
        }

        acknowledgment?.acknowledge()
    }
}