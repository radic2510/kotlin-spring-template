package com.yobi.standard.auth.handler

import com.yobi.standard.auth.jwt.TokenProvider
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder
import java.io.IOException
import jakarta.servlet.ServletException

@Component
class OAuth2SuccessHandler(
    private val tokenProvider: TokenProvider
) : AuthenticationSuccessHandler {

    companion object {
        private const val URI = "/auth/success"
    }

    @Throws(IOException::class, ServletException::class)
    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        val accessToken = tokenProvider.generateAccessToken(authentication)
        tokenProvider.generateRefreshToken(authentication, accessToken)

        val redirectUrl = UriComponentsBuilder.fromUriString(URI)
            .queryParam("accessToken", accessToken)
            .build()
            .toUriString()

        response.sendRedirect(redirectUrl)
    }
}
