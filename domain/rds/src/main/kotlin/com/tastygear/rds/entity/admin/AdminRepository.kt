package com.tastygear.rds.entity.admin

import com.tastygear.rds.entity.admin.Admin
import org.springframework.data.repository.CrudRepository

interface AdminRepository : CrudRepository<Admin, String> {
    fun findByUsername(username: String): Admin
}