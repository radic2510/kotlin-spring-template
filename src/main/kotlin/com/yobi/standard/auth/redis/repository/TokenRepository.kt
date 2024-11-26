package com.yobi.standard.auth.redis.repository

import com.yobi.standard.auth.redis.entity.Token
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TokenRepository : CrudRepository<Token, String> {
    fun findByAccessToken(accessToken: String): Token?
}
