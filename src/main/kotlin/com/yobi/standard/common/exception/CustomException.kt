package com.yobi.standard.common.exception

abstract class CustomException : RuntimeException {
    val errorCode: ErrorCode
    abstract override var message: String

    constructor(errorCode: ErrorCode) : super(errorCode.message) {
        this.errorCode = errorCode
        this.message = errorCode.message
    }

    constructor(errorCode: ErrorCode, message: String) : super(message) {
        this.errorCode = errorCode
        this.message = message
    }
}