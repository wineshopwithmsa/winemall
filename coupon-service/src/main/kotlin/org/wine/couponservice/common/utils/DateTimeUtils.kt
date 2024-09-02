package org.wine.couponservice.common.utils

import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateTimeUtils {

    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    /**
     * 문자열을 LocalDateTime으로 변환합니다.
     *
     * @param dateTimeString 변환할 날짜와 시간의 문자열
     * @return 변환된 LocalDateTime 객체
     * @throws IllegalArgumentException 문자열 형식이 올바르지 않은 경우
     */
    @JvmStatic
    fun parseToLocalDateTime(dateTimeString: String): LocalDateTime {
        return try {
            LocalDateTime.parse(dateTimeString, formatter)
        } catch (e: Exception) {
            throw IllegalArgumentException("Invalid date-time format: $dateTimeString", e)
        }
    }
    @JvmStatic
    fun laterNHours(num:Long): LocalDateTime {
        return LocalDateTime.now().plus(Duration.ofHours(num))
    }
}