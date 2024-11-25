package com.yobi.standard.member.repository

import com.yobi.standard.member.entity.Member
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MemberRepository : JpaRepository<Member, Long> {
    fun findByEmail(email: String): Optional<Member>
    fun findByMemberKey(memberKey: String): Optional<Member>
}
