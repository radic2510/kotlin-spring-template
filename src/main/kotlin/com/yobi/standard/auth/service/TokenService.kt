package com.yobi.standard.auth.service

import com.yobi.standard.auth.exception.TokenException
import com.yobi.standard.auth.redis.entity.Token
import com.yobi.standard.auth.redis.repository.TokenRepository
import org.springframework.boot.actuate.autoconfigure.cloudfoundry.CloudFoundryAuthorizationException.Reason.TOKEN_EXPIRED

@org.springframework.stereotype.Service
class TokenService(
    private val tokenRepository: TokenRepository
) {
    fun deleteRefreshToken(memberKey: String) {
        tokenRepository.deleteById(memberKey)
    }

    @org.springframework.transaction.annotation.Transactional
    fun saveOrUpdate(memberKey: String?, refreshToken: String?, accessToken: String?) {
        val token: Token = tokenRepository.findByAccessToken(accessToken)
            .map<Token>(java.util.function.Function { o: Token? ->
                o!!.updateRefreshToken(
                    refreshToken
                )
            })
            .orElseGet(java.util.function.Supplier { Token(memberKey, refreshToken, accessToken) })

        tokenRepository.save<Token?>(token)
    }

    fun findByAccessTokenOrThrow(accessToken: String?): Token? {
        return tokenRepository.findByAccessToken(accessToken)
            .orElseThrow { TokenException(TOKEN_EXPIRED) }
    }

    @org.springframework.transaction.annotation.Transactional
    fun updateToken(accessToken: String?, token: Token) {
        token.updateAccessToken(accessToken)
        tokenRepository.save<Token?>(token)
    }
}
