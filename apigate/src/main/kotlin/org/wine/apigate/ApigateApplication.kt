package org.wine.apigate

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ApigateApplication

fun main(args: Array<String>) {
    runApplication<ApigateApplication>(*args)
}
