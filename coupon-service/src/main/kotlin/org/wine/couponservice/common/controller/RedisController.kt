package org.wine.couponservice.common.controller

import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.wine.couponservice.common.service.RedisService
import java.util.*


@RestController
@RequestMapping("/res")
@RequiredArgsConstructor
class RedisController @Autowired constructor(
    private val redisService: RedisService
){

    @GetMapping("/test")
    fun test(): ResponseEntity<*> {

//        val dto: MyUserDto = MyUserDto
//            .username("skyer9")
//            .nickname("상구리")
//            .build()
        val dto: MyUserDto = MyUserDto("skyer9","123","e")

        redisService.putData("my", dto, 1000000L)
        val saved: Optional<MyUserDto> = redisService.getData("my", MyUserDto::class.java)
        return ResponseEntity.ok<Any>(saved)
    }
}