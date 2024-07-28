package org.wine.userservice.address.controller

import ApiResponse
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.*
import org.wine.userservice.address.dto.request.AddressRequestDto
import org.wine.userservice.address.service.AddressService

@RestController
@RequestMapping("/api/address")
class AddressController(private val addressService: AddressService) {
    @PostMapping("/v1")
    fun addAddress(@RequestHeader headers: HttpHeaders,@RequestBody addressRequestDto: AddressRequestDto):ApiResponse<Any> {
        val savedAddress = addressService.addAddress(addressRequestDto,headers)
        return ApiResponse.Success(data = savedAddress)
    }
    @GetMapping("/v1")
    fun getAddress(@RequestHeader headers: HttpHeaders):ApiResponse<Any>{
        val adddress = addressService.getAddress(headers);
        return ApiResponse.Success(data = adddress)
    }
}