package com.yobi.standard.auth.jwt

import com.yobi.standard.common.constants.TokenKey
import jakarta.servlet.ServletException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

@Component
class TokenAuthenticationFilter : OncePerRequestFilter() {
    private val tokenProvider: TokenProvider? = null

    override fun doFilterInternal(
        request: jakarta.servlet.http.HttpServletRequest?, response: jakarta.servlet.http.HttpServletResponse?,
        filterChain: jakarta.servlet.FilterChain?
    ) {
        val accessToken = resolveToken(request!!)

        if (tokenProvider!!.validateToken(accessToken)) {
            setAuthentication(accessToken)
        } else {
            val reissueAccessToken = tokenProvider.reissueAccessToken(accessToken)

            if (org.springframework.util.StringUtils.hasText(reissueAccessToken)) {
                setAuthentication(reissueAccessToken)
                response!!.setHeader(
                    org.springframework.http.HttpHeaders.AUTHORIZATION,
                    TokenKey.TOKEN_PREFIX + reissueAccessToken
                )
            }
        }

        filterChain!!.doFilter(request, response)
    }

    private fun setAuthentication(accessToken: kotlin.String?) {
        val authentication = tokenProvider!!.getAuthentication(accessToken)
        SecurityContextHolder.getContext().authentication = authentication
    }

    private fun resolveToken(request: jakarta.servlet.http.HttpServletRequest): kotlin.String? {
        val token = request.getHeader(org.springframework.http.HttpHeaders.AUTHORIZATION)
        if (org.springframework.util.ObjectUtils.isEmpty(token) || !token.startsWith(TokenKey.TOKEN_PREFIX)) {
            return null
        }
        return token.substring(TokenKey.TOKEN_PREFIX.length)
    }
}
