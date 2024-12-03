package com.yobi.standard.common.exception

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class ExceptionController: ResponseEntityExceptionHandler() {
    @ExceptionHandler(CommonException::class)
    internal fun handleException(msg: CommonException): ResponseEntity<ExceptionResponse<ErrorMessage>> {
        val error = ErrorMessage(message = msg.message, data = null)    // data는 추후 정의
        val exceptionResponse = ExceptionResponse<ErrorMessage>(
            code = msg.errorCode.httpStatus.value(),
            error = error
        )

        return ResponseEntity.ok(exceptionResponse)
    }

    internal class ErrorMessage(
        @JsonInclude(JsonInclude.Include.NON_ABSENT)
        val message: String?,
        val data: Map<String, Any>? = null
    )
}