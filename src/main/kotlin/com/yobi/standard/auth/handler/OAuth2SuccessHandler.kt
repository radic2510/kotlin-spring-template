package com.yobi.standard.auth.handler

import com.zerobase.funding.api.auth.jwt.TokenProvider

@RequiredArgsConstructor
@org.springframework.stereotype.Component
class OAuth2SuccessHandler : AuthenticationSuccessHandler {
    private val tokenProvider: TokenProvider? = null

    @kotlin.Throws(java.io.IOException::class, ServletException::class)
    override fun onAuthenticationSuccess(
        request: jakarta.servlet.http.HttpServletRequest?, response: jakarta.servlet.http.HttpServletResponse,
        authentication: org.springframework.security.core.Authentication?
    ) {
        val accessToken: kotlin.String? = tokenProvider.generateAccessToken(authentication)
        tokenProvider.generateRefreshToken(authentication, accessToken)

        val redirectUrl: kotlin.String = UriComponentsBuilder.fromUriString(OAuth2SuccessHandler.Companion.URI)
            .queryParam("accessToken", accessToken)
            .build().toUriString()

        response.sendRedirect(redirectUrl)
    }

    companion object {
        private const val URI = "/auth/success"
    }
}
