package com.tastygear.rds.entity.user

import com.fasterxml.jackson.annotation.JsonFormat
import com.tastygear.rds.entity.account.Account
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "USERS")
data class User(
    var name: String,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    var birthDay: LocalDateTime? = null,

    @Enumerated(EnumType.STRING)
    var gender: Gender = Gender.NONE,

    var region: String,

    var phoneNumber: String? = null,

    var leaveReason: String? = null,

) : Account(role = Role.USER) {

    enum class Gender {
        NONE, MALE, FEMALE
    }

    fun validate() {
        if (this.enabled.not()) throw IllegalAccessException("Invalid user status")
    }

    fun leave(reason: String?) {
        this.leaveReason = reason
        this.enabled = false
    }

    fun update(name: String, phoneNumber: String, gender: Gender, region: String) {
        this.name = name
        this.phoneNumber = phoneNumber
        this.gender = gender
        this.region = region
    }
}