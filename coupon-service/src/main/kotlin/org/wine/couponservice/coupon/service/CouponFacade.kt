package org.wine.couponservice.coupon.service

import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.wine.couponservice.common.utils.CouponUtils
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors
import java.util.concurrent.ThreadPoolExecutor
import java.util.stream.Collectors
import java.util.stream.LongStream

@Component
@Slf4j
class CouponFacade(
    private val couponService: CouponService
)  {
    private val log = LoggerFactory.getLogger(CouponFacade::class.java)

    /* fun asyncCreateCoupons(num:Long) {

        val futureList: List<CompletableFuture<Void>> = LongStream.range(0, num)
            .mapToObj { i: Long ->
                CompletableFuture.runAsync {
                    val randomId = CouponUtils.generateCouponId()
                    couponService.createFcfsCoupon(randomId)  // 반환값이 없음
                }
            }
            .collect(Collectors.toList())
        val allFutures = CompletableFuture.allOf(*futureList.toTypedArray())

        allFutures.join()  // 비동기 작업이 완료될 때까지 대기
    }*/
   val threadPoolExecutor: ThreadPoolExecutor = Executors.newFixedThreadPool(50) as ThreadPoolExecutor

    fun asyncCreateCoupons(num: Long) {
        val batchSize = 100  // 한 번에 처리할 작업의 수
        val numBatches = (num / batchSize).toInt() + 1

        for (batch in 0 until numBatches) {
            val start:Long =  (batch * batchSize).toLong()
//            val end = Math.min(start + batchSize, num)
            val end = Math.min(start+ batchSize,num)

            val futureList: List<CompletableFuture<Void>> = LongStream.range(start, end)
                .mapToObj { i: Long ->
                    CompletableFuture.runAsync({
                        val randomId = CouponUtils.generateCouponId()
                        couponService.createFcfsCoupon(randomId)
                    }, threadPoolExecutor)
                }
                .collect(Collectors.toList())

            val allFutures = CompletableFuture.allOf(*futureList.toTypedArray())
            allFutures.join()  // 현재 배치의 비동기 작업이 완료될 때까지 대기
        }
    }
}