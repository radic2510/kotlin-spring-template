package com.yobi.standard.auth.handler

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import java.io.IOException

class CustomAccessDeniedHandler : AccessDeniedHandler {

    private val log = LoggerFactory.getLogger(CustomAccessDeniedHandler::class.java)

    @Throws(IOException::class)
    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException
    ) {
        log.error("AccessDeniedException occurred.", accessDeniedException)
        response.sendError(HttpServletResponse.SC_FORBIDDEN, "접근 권한이 없습니다.")
    }
}
