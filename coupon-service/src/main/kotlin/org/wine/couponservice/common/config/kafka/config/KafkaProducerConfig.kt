package org.wine.couponservice.common.config.kafka.config

import org.apache.kafka.clients.producer.ProducerConfig
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate
import reactor.kafka.sender.SenderOptions

@Configuration
class KafkaProducerConfig(val kafkaProperties: KafkaProperties) {
    @Bean
    fun reactiveKafkaProducerTemplate() : ReactiveKafkaProducerTemplate<String, String> {
        val properties = mapOf(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to kafkaProperties.bootstrapServers,
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to kafkaProperties.producer.keySerializer,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to kafkaProperties.producer.valueSerializer
        )

        return ReactiveKafkaProducerTemplate(SenderOptions.create(properties))
    }
}