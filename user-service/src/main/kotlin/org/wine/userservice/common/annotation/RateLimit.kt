package org.wine.userservice.common.annotation

import java.util.concurrent.TimeUnit

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class RateLimit(val limit: Int, val timeUnit: TimeUnit)
