package com.yobi.standard.auth.controller

import com.yobi.standard.auth.dto.LoginResponse
import com.yobi.standard.auth.service.TokenService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController {
    private val tokenService: TokenService? = null
//    private val redisMessageService: RedisMessageService? = null

    @GetMapping("/auth/success")
    fun loginSuccess(loginResponse: @Valid LoginResponse): ResponseEntity<LoginResponse> {
        return ResponseEntity.ok<LoginResponse>(loginResponse)
    }

    @DeleteMapping("/auth/logout")
    fun logout(@AuthenticationPrincipal userDetails: UserDetails): ResponseEntity<Unit> {
        tokenService!!.deleteRefreshToken(userDetails.username)
//        redisMessageService.removeSubscribe(userDetails.getUsername())
        return ResponseEntity.noContent().build<Unit>()
    }
}
