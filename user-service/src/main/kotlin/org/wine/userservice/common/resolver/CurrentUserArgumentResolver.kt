package org.wine.userservice.common.resolver

import jakarta.servlet.http.HttpServletRequest
import org.springframework.core.MethodParameter
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import org.wine.userservice.common.annotation.CurrentUser
import org.wine.userservice.user.common.UserCommon

@Component
class CurrentUserArgumentResolver(
    private val userCommon: UserCommon
) : HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.getParameterAnnotation(CurrentUser::class.java) != null
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Long {
        val authHeader = webRequest.getNativeRequest(HttpServletRequest::class.java)?.getHeader(HttpHeaders.AUTHORIZATION)
        return userCommon.getJwtAccount2(authHeader)

//        return userCommon.getJwtAccount2(authHeader)
//        val auth = webRequest.getNativeRequest(HttpServletRequest::class.java)?.getHeader(HttpHeaders.AUTHORIZATION)
//        return userCommon.getJwtAccount(auth)
    }
}