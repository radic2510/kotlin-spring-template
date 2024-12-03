package com.yobi.standard.common.exception

import java.time.Instant

class ExceptionResponse<T>(
    val timestamp: Long = Instant.now().toEpochMilli(),
    val code: Int,
    val error: T?,
)
