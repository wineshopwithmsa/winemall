//package org.wine.userservice.user.jwt
//
//import jakarta.servlet.FilterChain
//import jakarta.servlet.ServletException
//import jakarta.servlet.http.HttpServletRequest
//import jakarta.servlet.http.HttpServletResponse
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
//import org.springframework.security.core.context.SecurityContextHolder
//import org.springframework.security.core.userdetails.UserDetails
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
//import org.springframework.stereotype.Component
//import org.springframework.web.filter.OncePerRequestFilter
//import org.wine.userservice.user.service.UserDetailsServiceImpl
//import java.io.IOException
//
//@Component
//class JwtAuthFilter(
//    private val jwtService: JwtService,
//    private val userDetailsServiceImpl: UserDetailsServiceImpl
//) : OncePerRequestFilter() {
//
//    @Throws(ServletException::class, IOException::class)
//    override fun doFilterInternal(
//        request: HttpServletRequest,
//        response: HttpServletResponse,
//        filterChain: FilterChain
//    ) {
//        val authHeader = request.getHeader("Authorization")
//        var token: String? = null
//        var username: String? = null
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            token = authHeader.substring(7)
//            username = jwtService.extractUsername(token)
//        }
//
//        if (username != null && SecurityContextHolder.getContext().authentication == null) {
//            val userDetails: UserDetails? = userDetailsServiceImpl.loadUserByUsername(username)
//            if (userDetails != null && jwtService.validateToken(token, userDetails)) {
//                val authenticationToken =
//                    UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
//                authenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
//                SecurityContextHolder.getContext().authentication = authenticationToken
//            }
//        }
//
//        filterChain.doFilter(request, response)
//    }
//}