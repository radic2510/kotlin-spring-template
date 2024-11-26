package com.yobi.standard.auth.dto

import com.yobi.standard.auth.exception.AuthException
import com.yobi.standard.common.exception.ErrorCode
import com.yobi.standard.common.utils.KeyGenerator
import com.yobi.standard.member.entity.Member
import com.yobi.standard.member.entity.Role


data class OAuth2UserInfo(
    val name: String,
    val email: String,
    val profile: String
) {
    companion object {
        fun of(registrationId: String, attributes: Map<String, Any>): OAuth2UserInfo {
            return when (registrationId) {
                "google" -> ofGoogle(attributes)
                "kakao" -> ofKakao(attributes)
                else -> throw AuthException(ErrorCode.ILLEGAL_REGISTRATION_ID)
            }
        }

        private fun ofGoogle(attributes: Map<String, Any>): OAuth2UserInfo {
            return OAuth2UserInfo(
                name = attributes["name"] as String,
                email = attributes["email"] as String,
                profile = attributes["picture"] as String
            )
        }

        private fun ofKakao(attributes: Map<String, Any>): OAuth2UserInfo {
            val account = attributes["kakao_account"] as? Map<*, *>
                ?: throw IllegalArgumentException("Invalid kakao_account format")
            val profile = account["profile"] as? Map<*, *>
                ?: throw IllegalArgumentException("Invalid profile format")

            return OAuth2UserInfo(
                name = profile["nickname"] as String,
                email = account["email"] as String,
                profile = profile["profile_image_url"] as String
            )
        }
    }

    fun toEntity(): Member {
        return Member(
            name = name,
            email = email,
            profile = profile,
            memberKey = KeyGenerator.generateKey(),
            role = Role.USER,
            address = null
        )
    }
}
