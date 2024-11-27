package com.yobi.standard.auth.service

import com.yobi.standard.auth.exception.TokenException
import com.yobi.standard.auth.redis.entity.Token
import com.yobi.standard.auth.redis.repository.TokenRepository
import com.yobi.standard.common.exception.ErrorCode
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TokenService(
    private val tokenRepository: TokenRepository
) {
    private val log = LoggerFactory.getLogger(TokenService::class.java)

    fun deleteRefreshToken(memberKey: String) {
        tokenRepository.deleteById(memberKey)
    }

    @Transactional
    fun saveOrUpdate(memberKey: String, refreshToken: String, accessToken: String): Token {
        return tokenRepository.findByAccessToken(accessToken)?.updateRefreshToken(refreshToken)
            ?: tokenRepository.save(Token(memberKey, refreshToken, accessToken))
    }

    fun findByAccessTokenOrThrow(accessToken: String): Token {
        return tokenRepository.findByAccessToken(accessToken)
            ?: throw TokenException(ErrorCode.TOKEN_EXPIRED)
    }

    @Transactional
    fun updateToken(accessToken: String, token: Token) {
        token.updateAccessToken(accessToken)
        tokenRepository.save(token)
    }
}
