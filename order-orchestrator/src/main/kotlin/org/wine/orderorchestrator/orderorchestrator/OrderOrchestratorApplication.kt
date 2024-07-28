package org.wine.orderorchestrator.orderorchestrator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class OrderOrchestratorApplication

fun main(args: Array<String>) {
    runApplication<OrderOrchestratorApplication>(*args)
}
