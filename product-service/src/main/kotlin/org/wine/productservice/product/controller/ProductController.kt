package org.wine.productservice.product.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/product-service/api/product")
class ProductController {
    @GetMapping
    fun getTest():String{
        return ""
    }
}