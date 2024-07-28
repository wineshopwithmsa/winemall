package org.wine.orderorchestrator.orderorchestrator.order.transcation.saga

import org.apache.kafka.common.network.Send
import org.wine.orderorchestrator.orderorchestrator.order.dto.OrderDto
import org.wine.orderorchestrator.orderorchestrator.order.transcation.TransactionEventPublisher
import org.wine.orderorchestrator.orderorchestrator.order.transcation.event.OrderCreateEvent
import org.wine.orderorchestrator.orderorchestrator.order.transcation.state.OrderPending
import org.wine.orderorchestrator.orderorchestrator.order.transcation.state.OrderSagaState
import reactor.core.publisher.Mono
import reactor.kafka.sender.SenderResult

class OrderSaga private constructor(
    private val eventPublisher : TransactionEventPublisher,
    private var orderState : OrderSagaState,
    val orderId : Long,
    val memberId : Long,
    val wineOrderList : List<OrderDto>,
    val couponId : Long,
    val key : String,
    var totalPrice: Int
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
            memberId = event.memberId,
            wineOrderList = event.wineOrderList,
            couponId = event.couponId,
            key = key,
            totalPrice = 0
        )
    }
    suspend fun operate() {
        orderState.operate(this)
    }

    suspend fun changeStateAndOperate(orderState: OrderSagaState) {
        this.orderState = orderState
        this.operate()
    }

    fun publishEvent(topic: String, key: String, event: Any): Mono<SenderResult<Void>> {
        return eventPublisher.publishEvent(topic, key, event)
    }
}