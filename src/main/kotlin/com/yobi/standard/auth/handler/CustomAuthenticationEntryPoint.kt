package com.yobi.standard.auth.handler

import org.springframework.security.web.AuthenticationEntryPoint


class CustomAuthenticationEntryPoint : AuthenticationEntryPoint {
    @Throws(java.io.IOException::class)
    override fun commence(
        request: jakarta.servlet.http.HttpServletRequest?, response: jakarta.servlet.http.HttpServletResponse,
        authException: org.springframework.security.core.AuthenticationException?
    ) {
//        log.error("AuthenticationException is occurred. ", authException)
        response.sendError(jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED, "인증에 실패하였습니다.")
    }
}
