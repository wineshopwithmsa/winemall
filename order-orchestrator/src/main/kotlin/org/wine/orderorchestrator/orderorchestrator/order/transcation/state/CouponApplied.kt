package org.wine.orderorchestrator.orderorchestrator.order.transcation.state

import kotlinx.coroutines.reactive.awaitSingle
import org.wine.orderorchestrator.orderorchestrator.order.transcation.event.ApplyCouponEvent
import org.wine.orderorchestrator.orderorchestrator.order.transcation.event.OrderCompletedEvent
import org.wine.orderorchestrator.orderorchestrator.order.transcation.saga.OrderSaga
import org.wine.orderorchestrator.orderorchestrator.order.transcation.topic.OrderTopic

class CouponApplied : OrderSagaState {
    override suspend fun operate(saga: OrderSaga) {
        saga.publishEvent(
            OrderTopic.ORDER_COMPLETED,
            saga.key,
            OrderCompletedEvent(saga.orderId)
        ).awaitSingle()
    }
}