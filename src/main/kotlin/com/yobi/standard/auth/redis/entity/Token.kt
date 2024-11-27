package com.yobi.standard.auth.redis.entity

import jakarta.persistence.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed

@RedisHash(value = Token.REDIS_HASH, timeToLive = Token.TTL)
data class Token (
    @Id
    val id: String,
    var refreshToken: String,
    @Indexed
    var accessToken: String,
) {
    companion object {
        const val REDIS_HASH = "jwt"
        const val TTL = 60 * 60 * 24 * 7L
    }

    fun updateRefreshToken(refreshToken: String): Token {
        this.refreshToken = refreshToken
        return this
    }

    fun updateAccessToken(accessToken: String) {
        this.accessToken = accessToken
    }
}
