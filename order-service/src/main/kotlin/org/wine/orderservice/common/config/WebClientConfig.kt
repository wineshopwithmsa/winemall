package org.wine.orderservice.common.config

import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient
import java.time.Duration
import java.util.concurrent.TimeUnit


@Configuration
class WebClientConfig(@Value("\${webclient.time-out}")
                       val webClinetTimeout : Int) {

    @Bean
    fun webClient(): WebClient{
        val httpClient : HttpClient = HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, webClinetTimeout)
            .responseTimeout(Duration.ofMillis(webClinetTimeout.toLong()))
            .doOnConnected{connection ->
                connection
                    .addHandlerFirst(ReadTimeoutHandler(webClinetTimeout.toLong(), TimeUnit.MILLISECONDS))
                    .addHandlerLast(WriteTimeoutHandler(webClinetTimeout.toLong(), TimeUnit.MILLISECONDS))
            }

        return WebClient.builder()
            .clientConnector(ReactorClientHttpConnector(httpClient))
            .build();
    }

}