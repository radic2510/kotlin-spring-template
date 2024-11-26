package com.yobi.standard.auth.service

import com.yobi.standard.auth.exception.AuthException
import com.yobi.standard.common.exception.ErrorCode
import com.yobi.standard.member.entity.Member
import com.yobi.standard.member.repository.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class AuthenticationService(
    private val memberRepository: MemberRepository
) {
    fun getMemberOrThrow(memberKey: String): Member {
        return memberRepository.findByMemberKey(memberKey)
            ?: throw AuthException(ErrorCode.MEMBER_NOT_FOUND)
    }

    fun checkAccess(memberKey: String, member: Member) {
        if (member.memberKey != memberKey) {
            throw AuthException(ErrorCode.NO_ACCESS)
        }
    }
}
