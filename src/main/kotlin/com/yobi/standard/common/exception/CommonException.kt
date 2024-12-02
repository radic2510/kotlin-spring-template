package com.yobi.standard.common.exception

abstract class CommonException(
    val errorCode: ErrorCode,
    override val message: String
) : RuntimeException(message) {

    constructor(errorCode: ErrorCode) : this(errorCode, errorCode.message)
}