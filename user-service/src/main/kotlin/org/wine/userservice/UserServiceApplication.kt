package org.wine.userservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.PropertySource

@SpringBootApplication
//@PropertySource("classpath:application.yml")
class UserServiceApplication

fun main(args: Array<String>) {
	runApplication<UserServiceApplication>(*args)
}
