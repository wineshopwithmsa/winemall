//package org.wine.userservice.security
//
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.security.authentication.AuthenticationManager
//import org.springframework.security.authentication.AuthenticationProvider
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider
//import org.springframework.security.authorization.AuthorizationDecision
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
//import org.springframework.security.config.annotation.web.builders.HttpSecurity
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
//import org.springframework.security.config.http.SessionCreationPolicy
//import org.springframework.security.core.Authentication
//import org.springframework.security.core.userdetails.UserDetailsService
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
//import org.springframework.security.crypto.password.PasswordEncoder
//import org.springframework.security.web.SecurityFilterChain
//import org.springframework.security.web.access.intercept.RequestAuthorizationContext
//import org.springframework.security.web.util.matcher.IpAddressMatcher
//import org.wine.userservice.user.jwt.JwtAuthFilter
//import org.wine.userservice.user.service.UserDetailsServiceImpl
//import java.util.function.Supplier
//
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity
//class SecurityConfigLegacy {
//    @Autowired
//    var jwtAuthFilter: JwtAuthFilter? = null
//    @Bean
//    fun userDetailsService(): UserDetailsService {
//        return UserDetailsServiceImpl()
//    }
//
//
//    @Bean
//    @Throws(Exception::class)
//    fun filterChain(http: HttpSecurity): SecurityFilterChain {
//        http
//            .csrf { csrf -> csrf.disable() }
//            .sessionManagement { sessionManagement ->
//                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//            }
//            .authorizeHttpRequests { authorizeRequests ->
//                authorizeRequests.anyRequest().permitAll()
//            }
//
//        return http.build()
//    }
//
//    private fun hasIpAddress(
//        authentication: Supplier<Authentication>,
//        `object`: RequestAuthorizationContext
//    ): AuthorizationDecision {
//        return AuthorizationDecision(ALLOWED_IP_ADDRESS_MATCHER.matches(`object`.request))
//    }
//
//    //    @Bean
//    //    protected SecurityFilterChain config(HttpSecurity http) throws Exception {
//    //        http.authorizeHttpRequests(authorize -> {
//    //            try {
//    //                authorize.anyRequest().permitAll();
//    ////                        .requestMatchers(WHITE_LIST).permitAll();
//    //            } catch (Exception e) {
//    //                e.printStackTrace();
//    //            }
//    //        })
//    //        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//    ////                .addFilter(getAuthenticationFilter());
//    //
//    //        return http.build();
//    //    }
//    @Bean
//    fun passwordEncoder(): PasswordEncoder {
//        return BCryptPasswordEncoder()
//    }
//
//    @Bean
//    fun authenticationProvider(): AuthenticationProvider {
//        val authenticationProvider = DaoAuthenticationProvider()
//        authenticationProvider.setUserDetailsService(userDetailsService())
//        authenticationProvider.setPasswordEncoder(passwordEncoder())
//        return authenticationProvider
//    }
//
//    @Bean
//    @Throws(Exception::class)
//    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager {
//        return config.authenticationManager
//    }
//
//    companion object {
//        const val ALLOWED_IP_ADDRESS: String = "0.0.0.0"
//        const val SUBNET: String = "/32"
//        val ALLOWED_IP_ADDRESS_MATCHER: IpAddressMatcher = IpAddressMatcher(ALLOWED_IP_ADDRESS + SUBNET)
//        const val IP_CHECK_PATH_PREFIX: String = "/user-service"
//
//        const val IP_CHECK_PATH_PATTERN: String = IP_CHECK_PATH_PREFIX + "/**"
//
//        private val WHITE_LIST = arrayOf(
//            "/**"
//        )
//    }
//}