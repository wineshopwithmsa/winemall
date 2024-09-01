//package org.wine.userservice.user.controller
//
//import org.springframework.http.ResponseEntity
//import org.springframework.kafka.core.KafkaTemplate
//import org.springframework.web.bind.annotation.PostMapping
//import org.springframework.web.bind.annotation.RequestMapping
//import org.springframework.web.bind.annotation.RestController
//
//@RestController
//@RequestMapping("/api/membercoupon")
//class MemberCouponController(
//    val kafkaTemplate: KafkaTemplate<String, String>
//
//) {
////    @PostMapping("/requestCoupon")
////    fun requestCoupon(@RequestParam userId: String): ResponseEntity<String> {
////        val couponId = redisService.dequeueCoupon()
////        if (couponId == null) {
////            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("No coupons available. Try again later.")
////        }
////
////        kafkaTemplate.send("couponRequests", userId to couponId)
////
////        return ResponseEntity.ok("Coupon request received")
////    }
//    @PostMapping("/request-coupon")
//    fun requestCoupon(): ResponseEntity<String> {
////        kafkaTemplate.send("coupon-requests", )
//        for (id in 0..1000) {
//            kafkaTemplate.send("coupon-requests", id.toString())
//        }
//        return ResponseEntity.ok("Coupon request sent")
//    }
//}