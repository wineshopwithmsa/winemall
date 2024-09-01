package org.wine.couponservice.coupon.event

import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.wine.couponservice.coupon.repository.CouponRepository
import java.util.*
import java.util.concurrent.TimeUnit

@Service

class CouponRequestListener(
    private val redisTemplate: RedisTemplate<String, String>,
    private val couponRepository: CouponRepository
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    companion object {
        private const val COUPON_KEY_PREFIX = "coupon:"
        private const val COUPON_LOCK_KEY = "coupon-lock"
    }

    @KafkaListener(topics = ["coupon-requests"], groupId = "coupon-group")
    fun listenCouponRequests(userId: String) {
        val lockValue = UUID.randomUUID().toString()
        val acquired = redisTemplate.opsForValue().setIfAbsent(COUPON_LOCK_KEY, lockValue, 10, TimeUnit.SECONDS)

        if (acquired == true) {
            try {
                val couponCode = redisTemplate.opsForList().leftPop(COUPON_KEY_PREFIX + "available")
                if (couponCode != null) {
                    // 쿠폰 발급 처리
                    redisTemplate.opsForValue().set("user:$userId:coupon", couponCode)

                    updateCouponInDatabase(couponCode)
                    // RDBMS 업데이트를 비동기적으로 처리
//                    CompletableFuture.runAsync {  }
                }
            } finally {
                // Ensure the lock is released only if it was acquired by this process
                if (lockValue == redisTemplate.opsForValue().get(COUPON_LOCK_KEY)) {
                    redisTemplate.delete(COUPON_LOCK_KEY)
                }
            }
        }
        
    }
    @Transactional
    fun updateCouponInDatabase(couponCode: String) {
        logger.info("couponCode = {}",couponCode)
        couponRepository.updateUserCouponByCode(couponCode)
//        var coupon = couponRepository.findByCouponCode(couponCode)
//        logger.info("coupon = {}",coupon)
//        coupon.isIssued = true
//        couponRepository.save(coupon)
    }
}