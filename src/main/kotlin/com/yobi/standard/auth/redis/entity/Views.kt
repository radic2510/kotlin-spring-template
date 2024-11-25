package com.yobi.standard.auth.redis.entity

import jakarta.persistence.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash(value = "views")
class Views {
    @Id
    val id: String? = null

    var count: Int? = null

    fun increaseCount(): Views {
        count = count!! + 1
        return this
    }
}
