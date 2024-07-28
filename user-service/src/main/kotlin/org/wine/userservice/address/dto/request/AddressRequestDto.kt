package org.wine.userservice.address.dto.request

import java.io.Serializable

/**
 * DTO for {@link org.wine.userservice.address.entity.Address}
 */
data class AddressRequestDto(
    val addressId: Long = 0L,
    val address: String? = null,
    val addressDetail: String? = null,
    val postalCode: String? = null,
    val city: String? = null,
    val addrOrder: Int? = null,
    val memberId:Long
) : Serializable