package com.yobi.standard.auth.redis.entity

import jakarta.persistence.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed

@RedisHash(value = "jwt", timeToLive = 60 * 60 * 24 * 7)
data class Token (
    @Id
    var id: String? = null,

    var refreshToken: String? = null,

    @Indexed
    private var accessToken: String? = null

) {
    fun updateRefreshToken(refreshToken: String?): Token {
        this.refreshToken = refreshToken
        return this
    }

    fun updateAccessToken(accessToken: String?) {
        this.accessToken = accessToken
    }
}
