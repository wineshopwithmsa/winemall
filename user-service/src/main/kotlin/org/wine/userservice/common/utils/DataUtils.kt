package org.wine.userservice.common.utils

object DataUtils {
    @JvmStatic
    fun generateRandomString(length: Int): String {
        val chars = ('a'..'z').toList()  // a부터 z까지 문자 리스트 생성
        return (1..length)
            .map { chars.random() }  // 랜덤으로 문자 선택
            .joinToString("")  // 선택된 문자들을 하나의 문자열로 합침
    }
}