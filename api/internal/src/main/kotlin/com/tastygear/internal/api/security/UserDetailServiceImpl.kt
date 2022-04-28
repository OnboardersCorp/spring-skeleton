package com.tastygear.internal.api.security

import com.tastygear.rds.entity.admin.AdminRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailServiceImpl(
    val adminRepository: AdminRepository,
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails = adminRepository.findByUsername(username)

}