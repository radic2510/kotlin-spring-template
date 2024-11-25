package com.yobi.standard.auth.jwt

import com.yobi.standard.auth.exception.TokenException
import jakarta.servlet.ServletException
import org.springframework.web.filter.OncePerRequestFilter


abstract class TokenExceptionFilter: OncePerRequestFilter() {
    @Throws(ServletException::class, java.io.IOException::class)
    fun doFilterInternal(
        request: jakarta.servlet.http.HttpServletRequest?, response: jakarta.servlet.http.HttpServletResponse?,
        filterChain: jakarta.servlet.FilterChain
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (e: TokenException) {
            response!!.sendError(e.errorCode?.httpStatus?.value(), e.message)
        }
    }
}
