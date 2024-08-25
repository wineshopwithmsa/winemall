package org.wine.userservice.common.resolver

import lombok.extern.slf4j.Slf4j
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.slf4j.LoggerFactory
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import org.wine.userservice.common.annotation.AdminOnly


@Aspect
@Component
@Slf4j
class AdminRoleResolver {
    private val logger = LoggerFactory.getLogger(javaClass)

    @Before("@annotation(adminOnly)")
    fun checkAdminRole(adminOnly: AdminOnly) {
        val authentication = SecurityContextHolder.getContext().authentication
        val principal = authentication.principal

        println("principal=="+principal.toString())
        println("UserDetails==")

        if (principal is UserDetails) {
            val roles = principal.authorities.map { it.authority }
            if (!roles.contains("ROLE_ADMIN")) {
                logger.error("오직 관리자만 접근 가능합니다.")
                throw IllegalAccessException("오직 관리자만 접근 가능합니다.")
            }
        } else {
            logger.error("오직 관리자만 접근 가능합니다.")
            throw IllegalAccessException("오직 관리자만 접근 가능합니다.")
        }
    }
}