package org.wine.userservice.address.controller

import ApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.wine.userservice.address.dto.request.AddressRequestDto
import org.wine.userservice.address.service.AddressService

@RestController
@RequestMapping("/api/address")
class AddressController(private val addressService: AddressService) {
    @PostMapping("/v1")
    fun addAddress(@RequestBody addressRequestDto: AddressRequestDto):ApiResponse<Any> {
        addressService.addAddress(addressRequestDto)
        return ApiResponse.Success(data = 0)
    }
    @GetMapping("/v1")
    fun getAddress():ApiResponse<Any>{
        return ApiResponse.Success(data = 0)
    }
}