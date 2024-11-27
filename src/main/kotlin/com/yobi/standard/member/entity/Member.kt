package com.yobi.standard.member.entity

import com.yobi.standard.common.entity.BaseTimeEntity
import com.yobi.standard.member.dto.MemberUpdateRequest
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "member")
data class Member (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, length = 50)
    var name: String,

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(nullable = false)
    val profile: String,

    @Column(nullable = false, unique = true)
    val memberKey: String,

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    val role: Role,

    @Embedded
    var address: Address? = null,
) : BaseTimeEntity() {

    fun address(address: Address) {
        this.address = address
    }

    fun updateMember(request: MemberUpdateRequest) {
        name = request.name
        request.address?.let {
            address = it.toEntity()
        }
    }

}