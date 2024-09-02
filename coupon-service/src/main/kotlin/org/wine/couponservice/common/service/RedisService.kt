package org.wine.couponservice.common.service

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import java.util.*
import java.util.concurrent.TimeUnit


@Service
class RedisService @Autowired constructor(
    private val redisTemplate: RedisTemplate<String, Any>,
    private val mapper: ObjectMapper
) {


    fun putData(key: String, value: Any?, expiredTime: Long?) {
        try {
            val jsonString = mapper!!.writeValueAsString(value)
            redisTemplate!!.opsForValue()[key, jsonString, expiredTime!!] = TimeUnit.MILLISECONDS
        } catch (e: JsonProcessingException) {
            throw RuntimeException("Invalid json format: ", e)
        }
    }

    fun <T> getData(key: String?, valueType: Class<T>?): Optional<T & Any> {
        try {
            val jsonString = redisTemplate!!.opsForValue()[key!!] as String?
            if (StringUtils.hasText(jsonString)) {
                return Optional.ofNullable(mapper!!.readValue(jsonString, valueType))
            }
            return Optional.empty()
        } catch (e: JsonProcessingException) {
            throw RuntimeException("Invalid json format: ", e)
        }
    }
    fun pushToList(key: String, value: Any) {
        try {
            val jsonString = mapper.writeValueAsString(value)
            redisTemplate.opsForList().rightPush(key, jsonString)
        } catch (e: JsonProcessingException) {
            throw RuntimeException("Invalid json format: ", e)
        }
    }
    fun remove(key: String) {
        redisTemplate!!.delete(key)
    }
}