package com.tastygear.rds.entity.admin

import com.tastygear.rds.entity.account.Account
import com.tastygear.rds.entity.user.Role
import javax.persistence.Entity

@Entity
data class Admin(
    var name: String,
) : Account(role = Role.ADMIN) {

    fun validate() {
        if (this.enabled.not()) throw IllegalAccessException("Invalid admin status")
    }
}