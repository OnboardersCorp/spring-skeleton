package com.tastygear.rds.entity.user

import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.PagingAndSortingRepository

interface UserRepository : PagingAndSortingRepository<User, String>, JpaSpecificationExecutor<User> {

    fun findByUsername(username: String): User

}