package org.wine.productservice.kafka.listener

import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.launch
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.hibernate.query.Order
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.listener.AcknowledgingMessageListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component
import org.wine.productservice.config.boundedElasticScope
import org.wine.productservice.kafka.OrderTopic
import org.wine.productservice.kafka.event.CheckStockCompleted
import org.wine.productservice.kafka.event.CheckStockEvent
import org.wine.productservice.kafka.event.CheckStockFailed
import org.wine.productservice.kafka.publisher.TransactionEventPublisher
import org.wine.productservice.wine.service.WineSaleService


@Component
class CheckStockEventListener(
    private val objectMapper: ObjectMapper,
    private val transactionEventPublisher: TransactionEventPublisher,
    private val wineSaleService: WineSaleService
): AcknowledgingMessageListener<String, String> {

    private val logger = LoggerFactory.getLogger(javaClass)
    @KafkaListener(topics = [OrderTopic.CHECK_STOCK], groupId = "order")
    override fun onMessage(data: ConsumerRecord<String, String>, acknowledgment: Acknowledgment?) {
        val (key, event) = data.key() to objectMapper.readValue(data.value(), CheckStockEvent::class.java)
        logger.info("Topic: ${OrderTopic.CHECK_STOCK}, key = $key, event: $event")
        try{
            val totalPrice : Int = wineSaleService.checkStockAndSubtractStock(event)
            transactionEventPublisher.publishEvent(
                topic = OrderTopic.CHECK_STOCK_COMPLETED,
                key = key,
                event = CheckStockCompleted(event.orderId, totalPrice)
            )
        }
        catch (e: Exception){
            logger.info("Check Stock Failed, error: "+ e.message)

            transactionEventPublisher.publishEvent(
                topic = OrderTopic.CHECK_STOCK_FAILED,
                key = key,
                event = CheckStockFailed(event.orderId, e.message!!)
            )
        }.let{
            boundedElasticScope.launch {
                it.awaitFirstOrNull()
            }
        }

        acknowledgment?.acknowledge()
    }
}