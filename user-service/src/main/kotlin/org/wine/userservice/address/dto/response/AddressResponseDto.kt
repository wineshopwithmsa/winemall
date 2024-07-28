package org.wine.userservice.address.dto.response

import org.wine.userservice.address.entity.Address
import org.wine.userservice.user.dto.response.MemberDto
import java.io.Serializable

/**
 * DTO for {@link org.wine.userservice.address.entity.Address}
 */
data class AddressResponseDto(
    val addressId: Long = 0L,
    val address: String? = null,
    val addressDetail: String? = null,
    val postalCode: String? = null,
    val city: String? = null,
    val addrOrder: Int? = null,
    val memberDto: MemberDto
) : Serializable{
    companion object {
        fun mapToAddressResponseDto(address: Address): AddressResponseDto {
            return AddressResponseDto(
                addressId = address.addressId,
                address = address.address,
                addressDetail = address.addressDetail,
                postalCode = address.postalCode,
                city = address.city,
                addrOrder = address.addrOrder,
                memberDto = MemberDto(
                    userId = address.member.getUserId(),
                    nickName = address.member.getNickName(),
                    email = address.member.getEmail()
                )
            )
        }
    }
}