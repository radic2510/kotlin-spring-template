package com.yobi.standard.auth.dto.model

import com.yobi.standard.member.entity.Member
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.core.user.OAuth2User

@JvmRecord
data class PrincipalDetails(
    val member: Member?,
    val attributes: MutableMap<String?, Any?>?,
    val attributeKey: String?
) : OAuth2User, UserDetails {
    override fun getName(): String? {
        return attributes!![attributeKey].toString()
    }

    override fun getAttributes(): MutableMap<String?, Any?>? {
        return attributes
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority?> {
        return mutableListOf<SimpleGrantedAuthority?>(
            SimpleGrantedAuthority(member!!.role.key)
        )
    }

    override fun getPassword(): String? {
        return null
    }

    override fun getUsername(): String {
        return member!!.memberKey
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}