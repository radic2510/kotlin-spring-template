package com.yobi.standard.auth.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

@Schema(description = "로그인 응답 DTO")
data class LoginResponse(
    @field:NotBlank
    @Schema(description = "액세스 토큰", example = "eyJhbGciOiJIUzI")
    val accessToken: String
)
