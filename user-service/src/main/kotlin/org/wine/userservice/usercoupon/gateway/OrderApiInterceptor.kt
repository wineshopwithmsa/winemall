//package org.wine.userservice.usercoupon.gateway
//
//import feign.RequestInterceptor
//import feign.RequestTemplate
//import lombok.Value
//
//class CouponApiInterceptor: RequestInterceptor(
//    @Value("\${order.api.token}")
//    private val apiToken: String? = null
//) {
//    override fun apply(template: RequestTemplate?) {
//        template?.header("authorization", apiToken)
//    }
//}