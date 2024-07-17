package org.wine.productservice.wine.entity

enum class UserType(val value: String) {
    SELLER("2"),
    CONSUMER("1");

    companion object {
        fun fromValue(value: String): UserType? {
            return entries.find { it.value == value }
        }
    }
}