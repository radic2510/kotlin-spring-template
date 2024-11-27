package com.yobi.standard.auth.dto

import jakarta.validation.constraints.NotBlank

data class LoginResponse(
    @field:NotBlank val accessToken: String
)
