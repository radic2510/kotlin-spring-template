package com.yobi.standard.auth.dto

import jakarta.validation.constraints.NotBlank

@JvmRecord
data class LoginResponse(val accessToken: @NotBlank String?)
