package com.yobi.standard.common.utils

import java.util.*

object KeyGenerator {
    fun generateKey(): String = UUID.randomUUID().toString().replace("-", "")
}

