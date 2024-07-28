package org.wine.userservice.address.service

import org.springframework.stereotype.Service
import org.wine.userservice.address.dto.request.AddressRequestDto
import org.wine.userservice.address.entity.Address
import org.wine.userservice.address.repository.AddressRepository
import org.wine.userservice.user.repository.MemberRepository

@Service
class AddressService(
    private val addressRepository: AddressRepository,
    private val memberRepository: MemberRepository
)  {
    fun addAddress(addressRequestDto: AddressRequestDto) {
        val member = memberRepository.findById(addressRequestDto.memberId)
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

        addressRepository.save(address)
    }

}