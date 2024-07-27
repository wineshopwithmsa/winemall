package org.wine.productservice.auth

import org.springframework.http.HttpHeaders

interface TokenExtractor {
    fun extract(headers: HttpHeaders): String?
}
