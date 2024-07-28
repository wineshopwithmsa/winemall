package org.wine.orderorchestrator.orderorchestrator.order.transcation.state

import kotlinx.coroutines.reactive.awaitSingle
import org.wine.orderorchestrator.orderorchestrator.order.transcation.event.CheckStockEvent
import org.wine.orderorchestrator.orderorchestrator.order.transcation.event.OrderCompletedEvent
import org.wine.orderorchestrator.orderorchestrator.order.transcation.saga.OrderSaga
import org.wine.orderorchestrator.orderorchestrator.order.transcation.topic.OrderTopic

class OrderPending : OrderSagaState {
    override suspend fun operate(saga: OrderSaga) {
        saga.publishEvent(
            OrderTopic.CEHCK_STOCK,//CheckStockEvent로 수정(테스트를 위해 order_completed로 임시 세팅)
            saga.key,
            CheckStockEvent(saga.orderId, saga.wineOrderList) //CheckStockEvent로 수정
        ).awaitSingle()
    }

}