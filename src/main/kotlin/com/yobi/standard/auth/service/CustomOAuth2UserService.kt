package com.yobi.standard.auth.service

import com.yobi.standard.auth.dto.OAuth2UserInfo
import com.yobi.standard.auth.dto.model.PrincipalDetails
import com.yobi.standard.member.entity.Member
import com.yobi.standard.member.repository.MemberRepository
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CustomOAuth2UserService(
    private val memberRepository: MemberRepository
) : DefaultOAuth2UserService() {

    @Transactional
    @Throws(OAuth2AuthenticationException::class)
    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val oAuth2UserAttributes = super.loadUser(userRequest).attributes
        val registrationId = userRequest.clientRegistration.registrationId
        val userNameAttributeName = userRequest.clientRegistration.providerDetails
            .userInfoEndpoint.userNameAttributeName

        val oAuth2UserInfo = OAuth2UserInfo.of(registrationId, oAuth2UserAttributes)
        val member = getOrSave(oAuth2UserInfo)

        return PrincipalDetails(member, oAuth2UserAttributes, userNameAttributeName)
    }

    private fun getOrSave(oAuth2UserInfo: OAuth2UserInfo): Member {
        val member = memberRepository.findByEmail(oAuth2UserInfo.email)
            ?: oAuth2UserInfo.toEntity()
        return memberRepository.save(member)
    }
}
