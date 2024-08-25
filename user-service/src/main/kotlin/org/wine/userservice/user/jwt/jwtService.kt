package org.wine.userservice.user.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import jakarta.annotation.PostConstruct
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component
import org.wine.userservice.user.service.CustomUserDetails
import java.security.Key
import java.util.*
import java.util.function.Function


@Component
class JwtService(var environment: Environment) {

    @PostConstruct
    fun preConstructor() {
        secretKey = environment.getProperty("jwt.secret") as String
        accessTokenExpiretime = environment.getProperty("jwt.accesstoken.time")?.toLong() ?: 0L
    }

    fun extractUsername(token: String?): String {
        return extractClaim(token) { obj: Claims -> obj.subject }
    }

    fun extractExpiration(token: String?): Date {
        return extractClaim(token) { obj: Claims -> obj.expiration }
    }

    fun <T> extractClaim(token: String?, claimsResolver: Function<Claims, T>): T {
        val claims = extractAllClaims(token)
        return claimsResolver.apply(claims)
    }

    private fun extractAllClaims(token: String?): Claims {
        return Jwts
            .parserBuilder()
            .setSigningKey(signKey)
            .build()
            .parseClaimsJws(token)
            .body
    }

    private fun isTokenExpired(token: String?): Boolean {
        return extractExpiration(token).before(Date())
    }

    fun validateToken(token: String?, userDetails: CustomUserDetails): Boolean {
        val userId = extractUsername(token)
//        return (!isTokenExpired(token))
        return (userId.toLong() == userDetails.getUserId() && !isTokenExpired(token))
    }

    fun generateToken(username: String): String {
//        val authorities: String = authentication.getAuthorities().stream()
//            .map { obj: GrantedAuthority -> obj.authority }
//            .collect(Collectors.joining(","))

        val claims: MutableMap<String, Any?> = HashMap()

//        claims["auth"] = authorities

        return createToken(claims, username)
    }

    private fun createToken(claims: Map<String, Any?>, username: String): String {


        return Jwts.builder()
            .setClaims(claims)
            .setSubject(username)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + accessTokenExpiretime)) // 1일로 설정
            .signWith(signKey, SignatureAlgorithm.HS256).compact()
    }

    private val signKey: Key
        get() {
            val keyBytes = Decoders.BASE64.decode(secretKey)
            return Keys.hmacShaKeyFor(keyBytes)
        }

    companion object {
        lateinit var secretKey: String
        var accessTokenExpiretime: Long = 0
    }
}