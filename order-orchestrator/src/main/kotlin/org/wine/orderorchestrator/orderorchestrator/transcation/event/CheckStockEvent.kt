package org.wine.orderorchestrator.orderorchestrator.transcation.event

data class CheckStockEvent(
    val wineSaleId : Long,
    val quantity : Int
)
