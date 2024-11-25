package com.yobi.standard.auth.handler

import jakarta.servlet.ServletException
import org.springframework.security.web.authentication.AuthenticationFailureHandler


class OAuth2FailureHandler : AuthenticationFailureHandler {
    @Throws(java.io.IOException::class, ServletException::class)
    override fun onAuthenticationFailure(
        request: jakarta.servlet.http.HttpServletRequest?, response: jakarta.servlet.http.HttpServletResponse,
        exception: org.springframework.security.core.AuthenticationException?
    ) {
//        log.error("OAuth2 login fail. ", exception)
        response.sendError(jakarta.servlet.http.HttpServletResponse.SC_BAD_REQUEST, "소셜 로그인에 실패하였습니다.")
    }
}
