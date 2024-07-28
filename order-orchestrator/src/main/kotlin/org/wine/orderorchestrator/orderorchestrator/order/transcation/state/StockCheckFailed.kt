package org.wine.orderorchestrator.orderorchestrator.order.transcation.state

import kotlinx.coroutines.reactive.awaitSingle
import org.wine.orderorchestrator.orderorchestrator.order.transcation.event.OrderRollbackEvent
import org.wine.orderorchestrator.orderorchestrator.order.transcation.event.StockRollbackEvent
import org.wine.orderorchestrator.orderorchestrator.order.transcation.saga.OrderSaga
import org.wine.orderorchestrator.orderorchestrator.order.transcation.topic.OrderTopic

class StockCheckFailed : OrderSagaState {
    override suspend fun operate(saga: OrderSaga) {
        saga.publishEvent(
            OrderTopic.ORDER_ROLLBACK,
            saga.key,
            OrderRollbackEvent(saga.orderId)
        ).awaitSingle()
    }
}