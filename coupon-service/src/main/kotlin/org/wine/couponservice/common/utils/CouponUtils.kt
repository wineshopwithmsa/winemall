package org.wine.couponservice.common.utils

class CouponUtils {
    companion object {
        @JvmStatic
        fun generateRandomCouponId(): Long {
            return (100000000000L..999999999999L).random()
        }
        @JvmStatic
        fun generateCouponId(): String {
            val LENGTH = 16
            val CHAR_POOL = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"

            // Length for the rest of the coupon ID (excluding the starting 'w')
            val restLength = LENGTH - 1

            // Generate a random string of the desired length
            val randomString = (1..restLength)
                .map { CHAR_POOL.random() }
                .joinToString("")

            // Return the coupon ID starting with 'w'
            return "w$randomString"
        }
    }

}