package com.yobi.standard.member.service

import com.yobi.standard.common.exception.CommonException
import com.yobi.standard.common.exception.ErrorCode
import com.yobi.standard.member.dto.MemberUpdateRequest
import com.yobi.standard.member.dto.model.MemberDto
import com.yobi.standard.member.entity.Member
import com.yobi.standard.member.repository.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class MemberService(
    private val memberRepository: MemberRepository
) {

    fun getMember(memberKey: String): MemberDto {
        return MemberDto.fromEntity(findByMemberKey(memberKey))
    }

    fun updateMember(request: MemberUpdateRequest, memberKey: String): MemberDto {
        val member = findByMemberKey(memberKey)
        member.updateMember(request)
        return MemberDto.fromEntity(member)
    }

    private fun findByMemberKey(memberKey: String): Member = (memberRepository.findByMemberKey(memberKey)
        ?: throw CommonException(ErrorCode.MEMBER_NOT_FOUND))
}