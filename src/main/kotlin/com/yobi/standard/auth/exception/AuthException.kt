package com.yobi.standard.auth.exception

import com.yobi.standard.common.exception.CustomException
import com.yobi.standard.common.exception.ErrorCode


abstract class AuthException(errorCode: ErrorCode) : CustomException(errorCode)