package com.yobi.standard.common.exception

abstract class CustomException(
    val errorCode: ErrorCode,
    override val message: String
) : RuntimeException(message) {

    constructor(errorCode: ErrorCode) : this(errorCode, errorCode.message)
}