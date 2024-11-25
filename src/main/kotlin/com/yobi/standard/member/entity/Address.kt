package com.yobi.standard.member.entity

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class Address(
    var roadAddress: String? = null,
    var addressDetail: String? = null,

    @Column(length = 10)
    var zipcode: String? = null
)