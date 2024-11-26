package com.yobi.standard.member.dto.model

import com.yobi.standard.member.entity.Member

data class MemberDto(
    val name: String,
    val email: String,
    val profile: String,
    val address: AddressDto? = null
) {
    companion object {
        fun fromEntity(member: Member): MemberDto {
            return MemberDto(
                name = member.name,
                email = member.email,
                profile = member.profile,
                address = member.address?.let { AddressDto.fromEntity(it) }
            )
        }
    }
}