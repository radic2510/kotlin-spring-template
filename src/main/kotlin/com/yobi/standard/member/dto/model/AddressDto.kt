package com.yobi.standard.member.dto.model

import com.yobi.standard.member.entity.Address
import jakarta.validation.constraints.NotBlank

data class AddressDto(
    @field:NotBlank
    val roadAddress: String?,

    @field:NotBlank
    val addressDetail: String?,

    @field:NotBlank
    val zipcode: String?,
) {
    companion object {
        fun fromEntity(address: Address): AddressDto {
            return AddressDto(
                roadAddress = address.roadAddress,
                addressDetail = address.addressDetail,
                zipcode = address.zipcode
            )
        }
    }

    fun toEntity(): Address {
        return Address(
            roadAddress = roadAddress,
            addressDetail = addressDetail,
            zipcode = zipcode
        )
    }
}