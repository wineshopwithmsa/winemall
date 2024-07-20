package org.wine.productservice.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {
    @Bean
    fun openAPI(): OpenAPI = OpenAPI()
        .components(Components())
        .info(apiInfo())

    private fun apiInfo() = Info()
        .title("winemall - product service api")
        .description("winemall의 product service api 입니다.")
        .version("1.0.0")
}