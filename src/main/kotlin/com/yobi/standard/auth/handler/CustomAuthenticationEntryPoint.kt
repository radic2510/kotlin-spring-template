package com.yobi.standard.auth.handler

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.slf4j.LoggerFactory
import java.io.IOException


class CustomAuthenticationEntryPoint : AuthenticationEntryPoint {

    private val log = LoggerFactory.getLogger(CustomAuthenticationEntryPoint::class.java)

    @Throws(IOException::class)
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        log.error("AuthenticationException occurred.", authException)
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "인증에 실패하였습니다.")
    }
}
