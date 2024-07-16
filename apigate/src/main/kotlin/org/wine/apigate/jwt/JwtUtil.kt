//package org.wine.apigate.jwt
//
//import io.jsonwebtoken.*
//import io.jsonwebtoken.security.Keys
//import org.slf4j.LoggerFactory
//import org.springframework.beans.factory.annotation.Value
//import org.springframework.stereotype.Component
//import java.nio.charset.StandardCharsets
//import java.util.*
//import javax.crypto.SecretKey
//import javax.crypto.spec.SecretKeySpec
//
//@Component
//class JwtUtil(
//    @Value("\${spring.jwt.secret}") secret: String
//) {
//
//    private val secretKey: SecretKey = SecretKeySpec(secret.toByteArray(StandardCharsets.UTF_8), SignatureAlgorithm.HS256.jcaName)
//
//    companion object {
//        private val log = LoggerFactory.getLogger(JwtUtil::class.java)
//    }
//
//    // JWT 토큰을 입력으로 받아 토큰의 subject에서 사용자 이메일(email)을 추출
//    fun getEmail(token: String): String {
//        return Jwts.parserBuilder()
//            .setSigningKey(secretKey)
//            .build()
//            .parseClaimsJws(token)
//            .body
//            .subject
//    }
//
//    fun getExpTime(token: String): Long {
//        return Jwts.parserBuilder()
//            .setSigningKey(secretKey)
//            .build()
//            .parseClaimsJws(token)
//            .body
//            .expiration
//            .time
//    }
//
//    // AccessToken 유효성 검사
//    fun validateAccessToken(token: String): Boolean {
//        return try {
//            // 구문 분석 시스템의 시계가 JWT를 생성한 시스템의 시계 오차 고려
//            // 약 3분 허용.
//            val seconds = 3 * 60
//            val isExpired = Jwts.parserBuilder()
//                .setClock { Date() }
//                .setAllowedClockSkewSeconds(seconds.toLong())
//                .setSigningKey(secretKey)
//                .build()
//                .parseClaimsJws(token)
//                .body
//                .expiration
//                .before(Date())
//            if (isExpired) {
//                log.info("만료된 JWT 토큰입니다.")
//            }
//
//            // Jwt 통과
//            log.info("[*] Token Valid")
//            !isExpired
//        } catch (e: SecurityException) {
//            log.info("잘못된 JWT 서명입니다.")
//            false
//        } catch (e: MalformedJwtException) {
//            log.info("잘못된 JWT 서명입니다.")
//            false
//        } catch (e: ExpiredJwtException) {
//            log.info("만료된 JWT 토큰입니다.")
//            false
//        } catch (e: UnsupportedJwtException) {
//            log.info("지원되지 않는 JWT 토큰입니다.")
//            false
//        } catch (e: IllegalArgumentException) {
//            log.info("JWT 토큰이 잘못되었습니다.")
//            false
//        }
//    }
//}