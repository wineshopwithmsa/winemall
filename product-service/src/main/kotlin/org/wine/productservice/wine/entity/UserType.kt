package org.wine.productservice.wine.entity

enum class UserType(val value: String) {
    // TODO: User Type 값 변경 필요할 수 있음.
    SELLER("2"),
    CONSUMER("1");

    companion object {
        fun fromValue(value: String): UserType? {
            return entries.find { it.value == value }
        }
    }
}