package org.wine.orderservice.common.kafka.config

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.AcknowledgingMessageListener
import org.wine.orderservice.common.kafka.event.OrderCompletedEvent
import org.wine.orderservice.common.kafka.listener.order.OrderCompletedEventListener
import org.wine.orderservice.common.kafka.listener.order.OrderRollbackEventListener
import java.net.InetAddress
import java.net.UnknownHostException
import java.util.*

@Configuration
class KafkaConsumerConfig(
    val kafkaProperties: KafkaProperties
) {

    @Bean
    fun orderCreationEventListenerContainerFactory(orderCompletedEventListener: OrderCompletedEventListener): ConcurrentKafkaListenerContainerFactory<String, String> {
        return kafkaListenerContainerFactory(orderCompletedEventListener)
    }

    @Bean
    fun orderProductCheckFailedEventListenerContainerFactory(orderRollBackedEventListener: OrderRollbackEventListener): ConcurrentKafkaListenerContainerFactory<String, String> {
        return kafkaListenerContainerFactory(orderRollBackedEventListener)
    }

    fun kafkaListenerContainerFactory(listener: AcknowledgingMessageListener<String, String>): ConcurrentKafkaListenerContainerFactory<String, String> {
        val containerFactory = ConcurrentKafkaListenerContainerFactory<String, String>()
        containerFactory.consumerFactory = consumerFactory()
        containerFactory.containerProperties.ackMode = kafkaProperties.listener.ackMode

        return containerFactory
    }

    private fun consumerFactory() : ConsumerFactory<String, String> {
        return DefaultKafkaConsumerFactory(consumerProperties())
    }

    private fun consumerProperties(): Map<String, Any> {
        val hostName: String = try {
            InetAddress.getLocalHost().hostName + UUID.randomUUID().toString()
        } catch (e: UnknownHostException) {
            UUID.randomUUID().toString()
        }
        return hashMapOf(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to kafkaProperties.bootstrapServers,
            ConsumerConfig.GROUP_ID_CONFIG to kafkaProperties.consumer.groupId,
            ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG to kafkaProperties.consumer.enableAutoCommit,
            ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to kafkaProperties.consumer.autoOffsetReset,
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to kafkaProperties.consumer.keyDeserializer,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to kafkaProperties.consumer.valueDeserializer
        )
    }
}
