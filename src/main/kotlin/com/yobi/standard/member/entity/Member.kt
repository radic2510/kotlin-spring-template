package com.yobi.standard.member.entity

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

    @Column(length = 10, nullable = false)
    val name: String,

    @Column(length = 20, nullable = false)
    val email: String,

    @Column(nullable = false)
    val profile: String,

    @Column(nullable = false, unique = true)
    val memberKey: String,

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    val role: Role,

    @Embedded
    val address: Address? = null,
)