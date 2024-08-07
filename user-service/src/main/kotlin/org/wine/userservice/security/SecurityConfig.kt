//package org.wine.userservice.security
//
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.security.authentication.ReactiveAuthenticationManager
//import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager
//import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
//import org.springframework.security.config.web.server.ServerHttpSecurity
//import org.springframework.security.config.web.server.ServerHttpSecurity.AuthorizeExchangeSpec
//import org.springframework.security.core.userdetails.ReactiveUserDetailsService
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
//import org.springframework.security.crypto.password.PasswordEncoder
//import org.springframework.security.web.server.SecurityWebFilterChain
//import org.springframework.security.web.server.authorization.AuthorizationContext
//import org.springframework.security.web.util.matcher.IpAddressMatcher
//import org.springframework.web.server.ServerWebExchange
//import reactor.core.publisher.Mono
//
//
//@Configuration
//@EnableWebFluxSecurity
//@EnableReactiveMethodSecurity
//class SecurityConfig {
//    @Autowired
//    var jwtAuthFilter: JwtAuthFilter? = null
//
////    @Bean
////    fun userDetailsService(): ReactiveUserDetailsService? {
////        return
////    }
//    @Bean
//    fun userDetailsService(): ReactiveUserDetailsService{
//        return userDetailsService()
//    }
//
//    @Bean
//    fun securityWebFilterChain(
//        http: ServerHttpSecurity
//    ): SecurityWebFilterChain {
//        return http.authorizeExchange { exchanges: AuthorizeExchangeSpec ->
//            exchanges
//                .anyExchange().authenticated()
//        }
//            .build()
//    }
////    @Bean
////    fun securityWebFilterChain(http: SecurityWebFilterChain): SecurityWebFilterChain {
////        return http
////            .csrf { csrf -> csrf.disable() }
////            .authorizeExchange { authorize ->
////                authorize.anyExchange().permitAll()
////            }
////            .addFilterAt(jwtAuthFilter, SecurityWebFiltersOrder.AUTHENTICATION)
////            .build()
////    }
//
//    private fun hasIpAddress(
//        exchange: ServerWebExchange,
//        context: AuthorizationContext
//    ): Mono<Boolean> {
//        return Mono.just(ALLOWED_IP_ADDRESS_MATCHER.matches(exchange.request.remoteAddress?.address?.hostAddress))
//    }
//
//    @Bean
//    fun passwordEncoder(): PasswordEncoder {
//        return BCryptPasswordEncoder()
//    }
//
//    @Bean
//    fun reactiveAuthenticationManager(): ReactiveAuthenticationManager {
//        val authenticationManager = UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService())
//        authenticationManager.setPasswordEncoder(passwordEncoder())
//        return authenticationManager
//    }
//
//    companion object {
//        const val ALLOWED_IP_ADDRESS: String = "0.0.0.0"
//        const val SUBNET: String = "/32"
//        val ALLOWED_IP_ADDRESS_MATCHER: IpAddressMatcher = IpAddressMatcher(ALLOWED_IP_ADDRESS + SUBNET)
//        const val IP_CHECK_PATH_PREFIX: String = "/user-service"
//        const val IP_CHECK_PATH_PATTERN: String = IP_CHECK_PATH_PREFIX + "/**"
//
//        private val WHITE_LIST = arrayOf(
//            "/**"
//        )
//    }
//}