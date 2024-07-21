package org.wine.orderorchestrator.orderorchestrator.transcation.state

import org.wine.orderorchestrator.orderorchestrator.transcation.saga.OrderSaga

interface OrderSagaState {
    fun operate(saga: OrderSaga)
}