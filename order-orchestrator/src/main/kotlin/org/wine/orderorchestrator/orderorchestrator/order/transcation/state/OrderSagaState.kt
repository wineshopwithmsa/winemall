package org.wine.orderorchestrator.orderorchestrator.order.transcation.state

import org.wine.orderorchestrator.orderorchestrator.order.transcation.saga.OrderSaga

interface OrderSagaState {
    suspend fun operate(saga: OrderSaga)
}