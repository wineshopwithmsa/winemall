package org.wine.orderservice.common.exception

import jakarta.ws.rs.BadRequestException

class NoValueException (message: String) : RuntimeException(message)
