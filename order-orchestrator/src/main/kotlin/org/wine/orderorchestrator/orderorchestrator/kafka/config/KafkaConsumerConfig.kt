package org.wine.orderorchestrator.orderorchestrator.kafka.config

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.listener.AcknowledgingMessageListener
import org.wine.orderorchestrator.orderorchestrator.kafka.listener.order.ApplyCouponCompletedEvent
import org.wine.orderorchestrator.orderorchestrator.kafka.listener.order.ApplyCouponFailedListener
import org.wine.orderorchestrator.orderorchestrator.kafka.listener.order.CheckStockCompletedListener
import org.wine.orderorchestrator.orderorchestrator.kafka.listener.order.OrderCreationEventListener
import java.net.InetAddress
import java.net.UnknownHostException
import java.util.UUID

@Configuration
class KafkaConsumerConfig(
    val kafkaProperties: KafkaProperties) {

    @Bean
    fun applyCouponCompletedEventListenerContainerFactory(applyCouponCompletedEvent: ApplyCouponCompletedEvent): ConcurrentKafkaListenerContainerFactory<String, String> {
        return kafkaListenerContainerFactory(applyCouponCompletedEvent)
    }

    @Bean
    fun applyCouponFailedListenerContainerFactory(applyCouponFailedListener: ApplyCouponFailedListener): ConcurrentKafkaListenerContainerFactory<String, String> {
        return kafkaListenerContainerFactory(applyCouponFailedListener)
    }

    @Bean
    fun checkStockCompletedListenerContainerFactory(checkStockCompletedListener: CheckStockCompletedListener): ConcurrentKafkaListenerContainerFactory<String, String> {
        return kafkaListenerContainerFactory(checkStockCompletedListener)
    }

    @Bean
    fun checkStockFailedListenerContainerFactory(checkStockFailedListener: ApplyCouponFailedListener): ConcurrentKafkaListenerContainerFactory<String, String> {
        return kafkaListenerContainerFactory(checkStockFailedListener)
    }

    @Bean
    fun orderCreationEventListenerContainerFactory(orderCreationEventListener: OrderCreationEventListener): ConcurrentKafkaListenerContainerFactory<String, String> {
        return kafkaListenerContainerFactory(orderCreationEventListener)
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