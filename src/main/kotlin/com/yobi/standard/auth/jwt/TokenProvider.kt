package com.yobi.standard.auth.jwt


import lombok.RequiredArgsConstructor

@RequiredArgsConstructor
@org.springframework.stereotype.Component
class TokenProvider {
    @org.springframework.beans.factory.annotation.Value("\${jwt.key}")
    private val key: kotlin.String? = null
    private var secretKey: javax.crypto.SecretKey? = null
    private val tokenService: TokenService? = null

    @PostConstruct
    private fun setSecretKey() {
        secretKey = io.jsonwebtoken.security.Keys.hmacShaKeyFor(key!!.toByteArray())
    }

    fun generateAccessToken(authentication: org.springframework.security.core.Authentication): kotlin.String? {
        return generateToken(authentication, TokenProvider.Companion.ACCESS_TOKEN_EXPIRE_TIME)
    }

    fun generateRefreshToken(
        authentication: org.springframework.security.core.Authentication,
        accessToken: kotlin.String?
    ) {
        val refreshToken = generateToken(authentication, TokenProvider.Companion.REFRESH_TOKEN_EXPIRE_TIME)
        tokenService.saveOrUpdate(authentication.getName(), refreshToken, accessToken)
    }

    private fun generateToken(
        authentication: org.springframework.security.core.Authentication,
        expireTime: kotlin.Long
    ): kotlin.String? {
        val now = java.util.Date()
        val expiredDate = java.util.Date(now.getTime() + expireTime)

        val authorities = authentication.getAuthorities().stream()
            .map<kotlin.String?> { obj: GrantedAuthority? -> obj.getAuthority() }
            .collect(java.util.stream.Collectors.joining())

        return Jwts.builder()
            .subject(authentication.getName())
            .claim(TokenProvider.Companion.KEY_ROLE, authorities)
            .issuedAt(now)
            .expiration(expiredDate)
            .signWith<javax.crypto.SecretKey?>(secretKey, Jwts.SIG.HS512)
            .compact()
    }

    fun getAuthentication(token: kotlin.String?): org.springframework.security.core.Authentication {
        val claims: Claims = parseClaims(token)
        val authorities: kotlin.collections.MutableList<SimpleGrantedAuthority?> = getAuthorities(claims)

        val principal = org.springframework.security.core.userdetails.User(claims.getSubject(), "", authorities)
        return UsernamePasswordAuthenticationToken(principal, token, authorities)
    }

    private fun getAuthorities(claims: Claims): kotlin.collections.MutableList<SimpleGrantedAuthority?> {
        return kotlin.collections.mutableListOf<SimpleGrantedAuthority?>(
            SimpleGrantedAuthority(
                claims.get(TokenProvider.Companion.KEY_ROLE).toString()
            )
        )
    }

    fun reissueAccessToken(accessToken: kotlin.String?): kotlin.String? {
        if (org.springframework.util.StringUtils.hasText(accessToken)) {
            val token: Token = tokenService.findByAccessTokenOrThrow(accessToken)
            val refreshToken: kotlin.String? = token.getRefreshToken()

            if (validateToken(refreshToken)) {
                val reissueAccessToken = generateAccessToken(getAuthentication(refreshToken))
                tokenService.updateToken(reissueAccessToken, token)
                return reissueAccessToken
            }
        }
        return null
    }

    fun validateToken(token: kotlin.String?): kotlin.Boolean {
        if (!org.springframework.util.StringUtils.hasText(token)) {
            return false
        }

        val claims: Claims = parseClaims(token)
        return claims.getExpiration().after(java.util.Date())
    }

    private fun parseClaims(token: kotlin.String?): Claims {
        try {
            return Jwts.parser().verifyWith(secretKey).build()
                .parseSignedClaims(token).getPayload()
        } catch (e: ExpiredJwtException) {
            return e.getClaims()
        } catch (e: MalformedJwtException) {
            throw TokenException(INVALID_TOKEN)
        } catch (e: io.jsonwebtoken.security.SecurityException) {
            throw TokenException(INVALID_JWT_SIGNATURE)
        }
    }

    companion object {
        private val ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30L
        private val REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60L * 24 * 7
        private const val KEY_ROLE = "role"
    }
}
