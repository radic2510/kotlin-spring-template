package com.yobi.standard.auth.dto

import com.yobi.standard.auth.exception.AuthException
import com.yobi.standard.common.exception.ErrorCode
import com.yobi.standard.common.utils.generateKey
import com.yobi.standard.member.entity.Member
import com.yobi.standard.member.entity.Role

data class OAuth2UserInfo(
    val name: String?,
    val email: String?,
    val profile: String?
) {
    fun toEntity(): Member {
        return Member(
            name = name!!,
            email = email!!,
            profile = profile!!,
            memberKey = generateKey(),
            role = Role.USER
        )
    }

    companion object {
        fun of(registrationId: String, attributes: MutableMap<String?, Any?>): OAuth2UserInfo? {
            return when (registrationId) {
                "google" -> ofGoogle(attributes)
                "kakao" -> ofKakao(attributes)
                else -> throw AuthException(ErrorCode.ILLEGAL_REGISTRATION_ID)
            }
        }

        private fun ofGoogle(attributes: MutableMap<String?, Any?>): OAuth2UserInfo {
            return OAuth2UserInfo(
                name = attributes.get("name") as String?,
                email = attributes.get("email") as String?,
                profile = attributes.get("picture") as String?
            )
        }

        private fun ofKakao(attributes: MutableMap<String?, Any?>): OAuth2UserInfo {
            val account = attributes.get("kakao_account") as MutableMap<String?, Any?>
            val profile = account.get("profile") as MutableMap<String?, Any?>

            return OAuth2UserInfo(
                name = profile.get("nickname") as String?,
                email = account.get("email") as String?,
                profile = profile.get("profile_image_url") as String?
            )
        }
    }
}
