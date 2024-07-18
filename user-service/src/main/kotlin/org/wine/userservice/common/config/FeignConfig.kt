package org.wine.userservice.common.config

import org.springframework.cloud.openfeign.clientconfig.FeignClientConfigurer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FeignConfig {

    @Bean
    fun feignClientConfigurer(): FeignClientConfigurer {
        return object : FeignClientConfigurer {
            override fun inheritParentConfiguration(): Boolean {
                return false
            }
        }
    }

//    @Bean
//    fun orderApiInterceptor(): OrderApiInterceptor{
//        return OrderApiInterceptor()
//    }



}