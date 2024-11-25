package com.yobi.standard.auth.handler

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import java.io.IOException

class CustomAccessDeniedHandler : AccessDeniedHandler {
    @Throws(IOException::class)
    override fun handle(
        request: HttpServletRequest?, response: HttpServletResponse,
        accessDeniedException: AccessDeniedException?
    ) {
//        log.error("AccessDeniedException is occurred. ", accessDeniedException);
        response.sendError(HttpServletResponse.SC_FORBIDDEN, "접근 권한이 없습니다.")
    }
}
