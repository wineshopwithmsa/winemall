package org.wine.productservice.auth

interface TokenExtractor {
    fun extract(): String?
}
