package com.yobi.standard.member.repository

import com.yobi.standard.member.entity.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {
    fun findByEmail(email: String): Member?
    fun findByMemberKey(memberKey: String): Member?
}
