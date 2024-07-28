package org.wine.orderorchestrator.orderorchestrator.kafka.listener

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class TestListener {
    @KafkaListener(topics = ["sample-topic"])
    fun consume(@Payload data : String){
        print(data);
    }
}