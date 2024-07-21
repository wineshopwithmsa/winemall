package org.wine.orderorchestrator.orderorchestrator.transcation.state

import org.wine.orderorchestrator.orderorchestrator.transcation.saga.OrderSaga

interface OrderSagaState {
    suspend fun operate(saga: OrderSaga)
}