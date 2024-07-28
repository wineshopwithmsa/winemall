package org.wine.orderorchestrator.orderorchestrator.order.repository

import org.wine.orderorchestrator.orderorchestrator.order.transcation.saga.OrderSaga

interface SagaRepository {
    fun save(id: String, orderSaga: OrderSaga)

    fun updatePrice(id:String, price: Int)

    fun findById(id: String): OrderSaga?

    fun deleteById(id: String)
}