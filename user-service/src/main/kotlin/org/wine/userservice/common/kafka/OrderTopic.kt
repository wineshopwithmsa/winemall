package org.wine.userservice.common.kafka

object OrderTopic {
    const val ORDER_CREATED = "ORDER-CREATED"

    const val ORDER_FAILED = "ORDER-FAILED"
    const val ORDER_COMPLETED = "ORDER-COMPLETED"

    const val CHECK_STOCK = "CHECK-STOCK"

    const val  CHECK_STOCK_COMPLETED = "CHECK-STOCK-COMPLETED"
    const val  CHECK_STOCK_FAILED = "CHECK-STOCK-FAILED"

    const val APPLY_COUPON = "APPLY-COUPON"

    const val  APPLY_COUPON_COMPLETED = "CHECK-COUPON-COMPLETED"
    const val  APPLY_COUPON_FAILED = "CHECK-COUPON-FAILED"

    const val COUPON_ROLLBACK = "COUPON-ROLLBACK"
    const val STOCK_ROLLBACK = "STOCK-ROLLBACK"
    const val ORDER_ROLLBACK = "ORDER-ROLLBACK"
}