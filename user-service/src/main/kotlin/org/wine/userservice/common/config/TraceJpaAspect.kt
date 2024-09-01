package org.wine.userservice.common.config

import io.micrometer.tracing.Tracer
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component


@Aspect
@Component
class TraceJpaAspect(private val tracer: Tracer) {
    @Around("execution(* org.wine.userservice.*.repository.*.*(..))")
    @Throws(Throwable::class)
    fun traceJpaMethod(joinPoint: ProceedingJoinPoint): Any {
        val span = tracer.startScopedSpan("repository." + joinPoint.signature.name)
        // 메서드 인자 가져오기
        val args = joinPoint.args
        span.tag("method", joinPoint.signature.name)
        try {
            span.tag("args", args.contentToString())
        } catch (ignored: RuntimeException) {
            // string으로 형변환하지 못하는 경우는 예외 ignored 처리
        }
        span.tag("repository name", joinPoint.signature.declaringTypeName)
        try {
            span.event("Start repository")
            // 메서드 실행
            return joinPoint.proceed()
        } catch (throwable: Throwable) {
            span.error(throwable) // 예외 정보를 트레이스에 기록
            throw throwable
        } finally {
            span.event("End repository")
            span.end()
        }
    }
}