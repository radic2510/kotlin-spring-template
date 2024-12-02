package com.yobi.standard.auth.controller

import com.yobi.standard.auth.dto.LoginResponse
import com.yobi.standard.auth.service.TokenService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Auth-Controller", description = "로그인 관련 컨트롤러")
@RestController
class AuthController {
    private val tokenService: TokenService? = null
//    private val redisMessageService: RedisMessageService? = null

    @GetMapping("/auth/success")
    @Operation(summary = "로그인", description = "로그인을 성공하면 호출되는 API",
//        security = [SecurityRequirement(name = "bearerAuth")]
    )
    @ApiResponse(responseCode = "200", description = "성공",
        content = [Content(schema = Schema(implementation = LoginResponse::class))]
    )
    fun loginSuccess(@RequestBody loginResponse: @Valid LoginResponse): ResponseEntity<LoginResponse> {
        return ResponseEntity.ok<LoginResponse>(loginResponse)
    }

    @DeleteMapping("/auth/logout")
    @Operation(summary = "로그아웃", description = "로그아웃 API")
    @ApiResponse(responseCode = "200", description = "성공")
    fun logout(@AuthenticationPrincipal userDetails: UserDetails): ResponseEntity<Unit> {
        tokenService!!.deleteRefreshToken(userDetails.username)
//        redisMessageService.removeSubscribe(userDetails.getUsername())
        return ResponseEntity.noContent().build<Unit>()
    }
}
