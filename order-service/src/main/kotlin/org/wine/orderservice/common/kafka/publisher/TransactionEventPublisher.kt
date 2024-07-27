package org.wine.orderservice.common.kafka.publisher

import reactor.core.publisher.Mono
import reactor.kafka.sender.SenderResult

interface TransactionEventPublisher {
    fun publishEvent(topic: String, key: String, event: Any) : Mono<SenderResult<Void>>
}