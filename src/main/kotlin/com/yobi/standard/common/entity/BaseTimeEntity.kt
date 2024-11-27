package com.yobi.standard.common.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseTimeEntity {

    @JsonIgnore
    @CreatedDate
    var createdDate: LocalDateTime? = null

    @JsonIgnore
    @LastModifiedDate
    @Column(insertable = false)
    var modifiedDate: LocalDateTime? = null
}