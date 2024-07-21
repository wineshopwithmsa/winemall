package org.wine.orderorchestrator.orderorchestrator.transcation.state

import kotlinx.coroutines.reactive.awaitSingle
import org.wine.orderorchestrator.orderorchestrator.transcation.event.CheckStockEvent
import org.wine.orderorchestrator.orderorchestrator.transcation.saga.OrderSaga
import org.wine.orderorchestrator.orderorchestrator.transcation.topic.OrderTopic

class OrderPending : OrderSagaState {
    override suspend fun operate(saga: OrderSaga) {
        saga.publishEvent(
            OrderTopic.CHECK_STOCK,
            saga.key,
            CheckStockEvent(saga.wineSaleId, saga.quantity)
        ).awaitSingle()
    }

}