package com.yobi.standard.common.utils

import java.util.*

fun generateKey(): String {
    return UUID.randomUUID().toString().replace("-", "")
}

