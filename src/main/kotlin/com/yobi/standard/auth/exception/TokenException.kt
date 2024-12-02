package com.yobi.standard.auth.exception

import com.yobi.standard.common.exception.CommonException
import com.yobi.standard.common.exception.ErrorCode

class TokenException(errorCode: ErrorCode) : CommonException(errorCode)
