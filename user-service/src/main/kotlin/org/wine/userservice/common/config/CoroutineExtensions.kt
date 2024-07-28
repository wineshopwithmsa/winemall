package org.wine.userservice.common.config

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.reactor.asCoroutineDispatcher
import reactor.core.scheduler.Schedulers

val boundedElasticDispatcher = Schedulers.boundedElastic()
    .asCoroutineDispatcher()

val boundedElasticScope: CoroutineScope
    get() = CoroutineScope(boundedElasticDispatcher)