package org.wine.userservice.address.service

import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.wine.userservice.address.dto.request.AddressRequestDto
import org.wine.userservice.address.dto.response.AddressResponseDto
import org.wine.userservice.address.entity.Address
import org.wine.userservice.address.repository.AddressRepository
import org.wine.userservice.user.common.UserCommon
import org.wine.userservice.user.repository.MemberRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
@Transactional(readOnly = true)
class AddressService(
    private val addressRepository: AddressRepository,
    private val memberRepository: MemberRepository,
    private val userCommon: UserCommon
) {
    @Transactional
    suspend fun addAddress(addressRequestDto: AddressRequestDto, headers: HttpHeaders): Mono<AddressResponseDto> {
        val userId = userCommon.getJwtAccount(headers).awaitSingle()

        val addressMono = memberRepository.findById(userId)
            .switchIfEmpty(Mono.error(IllegalArgumentException("Member not found")))
            .flatMap { member ->
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

        return addressMono.map { savedAddress ->
            AddressResponseDto.mapToAddressResponseDto(savedAddress)
        }
    }

    suspend fun getAddress(headers: HttpHeaders): Flux<AddressResponseDto> {
        val userId = userCommon.getJwtAccount(headers).awaitSingle()
        return addressRepository.findByMemberUserId(userId)
            .map { AddressResponseDto.mapToAddressResponseDto(it) }
    }
}