package org.wine.orderorchestrator.orderorchestrator.order.repository

import org.springframework.stereotype.Component
import org.wine.orderorchestrator.orderorchestrator.order.transcation.saga.OrderSaga
import java.util.concurrent.ConcurrentHashMap

@Component
class SagaInMemoryRepository: SagaRepository {
    private val orderSagaMap: ConcurrentHashMap<String, OrderSaga> = ConcurrentHashMap()

    override fun save(id: String, orderSaga: OrderSaga) {
        orderSagaMap[id] = orderSaga
    }

    override fun updatePrice(id:String, price: Int){
        orderSagaMap[id]?.totalPrice  = price
    }
    override fun findById(id: String): OrderSaga? = orderSagaMap[id]

    override fun deleteById(id: String) {
        orderSagaMap.remove(id)
    }
}