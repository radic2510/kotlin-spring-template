package com.yobi.standard.auth.jwt

import com.yobi.standard.common.constants.TokenKey
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

@Component
class TokenAuthenticationFilter(
    private val tokenProvider: TokenProvider
) : OncePerRequestFilter() {

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val accessToken = resolveToken(request)

        if (tokenProvider.validateToken(accessToken)) {
            setAuthentication(accessToken)
        } else {
            val reissueAccessToken = tokenProvider.reissueAccessToken(accessToken)
            if (StringUtils.hasText(reissueAccessToken)) {
                setAuthentication(reissueAccessToken)
                response.setHeader(AUTHORIZATION, TokenKey.TOKEN_PREFIX + reissueAccessToken)
            }
        }

        filterChain.doFilter(request, response)
    }

    private fun setAuthentication(accessToken: String?) {
        if (accessToken != null) {
            val authentication: Authentication = tokenProvider.getAuthentication(accessToken)
            SecurityContextHolder.getContext().authentication = authentication
        }
    }

    private fun resolveToken(request: HttpServletRequest): String? {
        val token = request.getHeader(AUTHORIZATION)
        return if (!token.isNullOrEmpty() && token.startsWith(TokenKey.TOKEN_PREFIX)) {
            token.substring(TokenKey.TOKEN_PREFIX.length)
        } else {
            null
        }
    }
}
