package org.wine.userservice.address.service

import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.wine.userservice.address.dto.request.AddressRequestDto
import org.wine.userservice.address.dto.response.AddressResponseDto
import org.wine.userservice.address.entity.Address
import org.wine.userservice.address.repository.AddressRepository
import org.wine.userservice.user.common.UserCommon
import org.wine.userservice.user.repository.MemberRepository

@Service
class AddressService(
    private val addressRepository: AddressRepository,
    private val memberRepository: MemberRepository,
    private val userCommon: UserCommon
)  {
    @Transactional
    fun addAddress(addressRequestDto: AddressRequestDto,headers: HttpHeaders):AddressResponseDto {
        val userId = userCommon.getJwtAccount(headers).toLong()

        val member = memberRepository.findById(userId)
            .orElseThrow { IllegalArgumentException("Member not found") }

        val address = Address(
            addressId = addressRequestDto.addressId,
            address = addressRequestDto.address ?: "",
            addressDetail = addressRequestDto.addressDetail ?: "",
            postalCode = addressRequestDto.postalCode ?: "",
            city = addressRequestDto.city ?: "",
            addrOrder = addressRequestDto.addrOrder ?: 0,
            member = member
        )
        val savedAddress = addressRepository.save(address)
        return AddressResponseDto.mapToAddressResponseDto(savedAddress)
    }

    fun getAddress(headers: HttpHeaders): List<AddressResponseDto> {
        val userId = userCommon.getJwtAccount(headers).toLong()

        val address = addressRepository.findByMemberUserId(userId)
        return address.map { AddressResponseDto.mapToAddressResponseDto(it) }
    }


}