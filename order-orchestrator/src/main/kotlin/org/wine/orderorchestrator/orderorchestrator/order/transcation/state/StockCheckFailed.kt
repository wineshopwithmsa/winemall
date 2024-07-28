package org.wine.orderorchestrator.orderorchestrator.order.transcation.state

import kotlinx.coroutines.reactive.awaitSingle
import org.wine.orderorchestrator.orderorchestrator.order.transcation.event.OrderFailedEvent
import org.wine.orderorchestrator.orderorchestrator.order.transcation.saga.OrderSaga
import org.wine.orderorchestrator.orderorchestrator.order.transcation.topic.OrderTopic

class StockCheckFailed(
    val failureMessage: String
) : OrderSagaState {
    override suspend fun operate(saga: OrderSaga) {
        saga.publishEvent(
            OrderTopic.ORDER_FAILED,
            saga.key,
            OrderFailedEvent(saga.orderId, failureMessage)
        ).awaitSingle()
    }
}