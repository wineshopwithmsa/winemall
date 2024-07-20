package org.wine.orderorchestrator.orderorchestrator.transcation

import reactor.core.publisher.Mono
import reactor.kafka.sender.SenderResult

interface TranscationEventPublisher {

    fun publishEvent(topic: String, key: String, event: Any) : Mono<SenderResult<Void>>
}