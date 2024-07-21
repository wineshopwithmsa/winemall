package org.wine.orderorchestrator.orderorchestrator.transcation.saga

import org.apache.kafka.common.network.Send
import org.wine.orderorchestrator.orderorchestrator.transcation.TransactionEventPublisher
import org.wine.orderorchestrator.orderorchestrator.transcation.event.OrderCreateEvent
import org.wine.orderorchestrator.orderorchestrator.transcation.state.OrderPending
import org.wine.orderorchestrator.orderorchestrator.transcation.state.OrderSagaState
import reactor.core.publisher.Mono
import reactor.kafka.sender.SenderResult

class OrderSaga private constructor(
    private val eventPublisher : TransactionEventPublisher,
    private var orderState : OrderSagaState,
    val orderId : Long,
    val customerId : Long,
    val wineSaleId : Long,
    val couponId : Long,
    val quantity : Int,
    val key : String
){

    companion object{
        fun init(
            eventPublisher : TransactionEventPublisher,
            key : String,
            event : OrderCreateEvent
        ): OrderSaga = OrderSaga(
            eventPublisher = eventPublisher,
            orderState = OrderPending(),
            orderId = event.orderId,
            customerId = event.customerId,
            wineSaleId = event.winSaleId,
            quantity = event.quantity,
            couponId = event.couponId,
            key = key
        )
    }

    fun publishEvent(topic: String, key: String, event: Any): Mono<SenderResult<Void>> {
        return eventPublisher.publishEvent(topic, key, event)
    }
}