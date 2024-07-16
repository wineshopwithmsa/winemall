package org.wine.productservice.wine.service

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Service
import java.security.Key

@Service
class JwtTokenProvider {
    fun getAccount(token: String): String {
        return Jwts.parserBuilder().setSigningKey(signKey).build()
            .parseClaimsJws(token).body.subject.toString()
    }
    private val signKey: Key
        get() {
            val keyBytes = Decoders.BASE64.decode("357638792F423F4428472B4B6250655368566D597133743677397A2443264629")
            return Keys.hmacShaKeyFor(keyBytes)
        }
}