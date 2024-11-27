package com.yobi.standard.member.dto

import com.yobi.standard.member.dto.model.AddressDto
import jakarta.validation.constraints.NotBlank

data class MemberUpdateRequest(
    @field:NotBlank
    val name: String,
    val address: AddressDto?
)