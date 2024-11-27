package com.yobi.standard.auth.jwt

import com.yobi.standard.auth.exception.TokenException
import com.yobi.standard.auth.service.TokenService
import com.yobi.standard.common.exception.ErrorCode
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SecurityException
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import java.util.Date
import javax.crypto.SecretKey

@Component
class TokenProvider(
    private val tokenService: TokenService,

    @Value("\${jwt.key}")
    private val key: String
) {
    private val secretKey: SecretKey = Keys.hmacShaKeyFor(key.toByteArray())

    companion object {
        private const val ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30L
        private const val REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60L * 24 * 7
        private const val KEY_ROLE = "role"
    }

    fun generateAccessToken(authentication: Authentication): String {
        return generateToken(authentication, ACCESS_TOKEN_EXPIRE_TIME)
    }

    fun generateRefreshToken(authentication: Authentication, accessToken: String) {
        val refreshToken = generateToken(authentication, REFRESH_TOKEN_EXPIRE_TIME)
        tokenService.saveOrUpdate(authentication.name, refreshToken, accessToken)
    }

    private fun generateToken(authentication: Authentication, expireTime: Long): String {
        val now = Date()
        val expiredDate = Date(now.time + expireTime)

        val authorities = authentication.authorities
            .joinToString(",") { it.authority }

        return Jwts.builder()
            .subject(authentication.name)
            .claim(KEY_ROLE, authorities)
            .issuedAt(now)
            .expiration(expiredDate)
            .signWith(secretKey)
            .compact()
    }

    fun getAuthentication(token: String): Authentication {
        val claims = parseClaims(token)
        val authorities = getAuthorities(claims)

        val principal = User(claims.subject, "", authorities)
        return UsernamePasswordAuthenticationToken(principal, token, authorities)
    }

    private fun getAuthorities(claims: Claims): List<SimpleGrantedAuthority> {
        return listOf(SimpleGrantedAuthority(claims[KEY_ROLE].toString()))
    }

    fun reissueAccessToken(accessToken: String?): String? {
        if (!StringUtils.hasText(accessToken)) {
            return null // null 반환으로 처리
        }

        val token = tokenService.findByAccessTokenOrThrow(accessToken!!)
        val refreshToken = token.refreshToken

        if (validateToken(refreshToken)) {
            val reissuedAccessToken = generateAccessToken(getAuthentication(refreshToken))
            tokenService.updateToken(reissuedAccessToken, token)
            return reissuedAccessToken
        }

        return null
    }

    fun validateToken(token: String?): Boolean {
        if (!StringUtils.hasText(token)) return false

        val claims = parseClaims(token!!)
        return claims.expiration.after(Date())
    }

    private fun parseClaims(token: String): Claims {
        return try {
            Jwts.parser().verifyWith(secretKey).build()
                .parseSignedClaims(token).getPayload()
        } catch (e: ExpiredJwtException) {
            e.claims
        } catch (e: MalformedJwtException) {
            throw TokenException(ErrorCode.INVALID_TOKEN)
        } catch (e: SecurityException) {
            throw TokenException(ErrorCode.INVALID_JWT_SIGNATURE)
        }
    }
}
