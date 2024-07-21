package org.wine.orderservice.order.kafka.publisher

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate
import org.springframework.stereotype.Component
import org.wine.orderservice.order.transaction.TransactionEventPublisher
import reactor.core.publisher.Mono
import reactor.kafka.sender.SenderResult

@Component
class KafkaEventPublisher(
    private val reactiveKafkaProducerTemplate : ReactiveKafkaProducerTemplate<String, String>,
    private val objectMapper : ObjectMapper): TransactionEventPublisher {

    override fun publishEvent(topic : String, key : String, event : Any): Mono<SenderResult<Void>> {
        return reactiveKafkaProducerTemplate.send(topic, key, objectMapper.writeValueAsString(event))
    }
}