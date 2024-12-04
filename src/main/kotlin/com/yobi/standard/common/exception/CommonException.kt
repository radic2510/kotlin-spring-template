package com.yobi.standard.common.exception

open class CommonException(
    val errorCode: ErrorCode,
    override val message: String
) : RuntimeException(message) {

    constructor(errorCode: ErrorCode) : this(errorCode, errorCode.message)
}