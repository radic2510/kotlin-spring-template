package com.yobi.standard.auth.handler

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.slf4j.LoggerFactory
import java.io.IOException
import jakarta.servlet.ServletException

class OAuth2FailureHandler : AuthenticationFailureHandler {

    private val log = LoggerFactory.getLogger(OAuth2FailureHandler::class.java)

    @Throws(IOException::class, ServletException::class)
    override fun onAuthenticationFailure(
        request: HttpServletRequest,
        response: HttpServletResponse,
        exception: AuthenticationException
    ) {
        log.error("OAuth2 login failed.", exception)
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "소셜 로그인에 실패하였습니다.")
    }
}
