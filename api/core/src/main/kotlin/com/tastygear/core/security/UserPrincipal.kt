package com.tastygear.core.security

import com.tastygear.rds.entity.admin.Admin
import com.tastygear.rds.entity.user.Role
import com.tastygear.rds.entity.user.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserPrincipal(
    private val id: String,
    private val password: String,
    private val authorities: Collection<GrantedAuthority>
): UserDetails {
    override fun getAuthorities() = this.authorities

    override fun getPassword() = this.password

    override fun getUsername() = this.id

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = true

    companion object {
        fun createForUser(user: User) =
            UserPrincipal(
                user.id!!,
                user.password,
                listOf(Role.USER).asSequence().map { SimpleGrantedAuthority("ROLE_$it") }.toMutableList()
            )

        fun createForAdmin(admin: Admin) =
            UserPrincipal(
                admin.id!!,
                admin.password,
                listOf(Role.USER, Role.ADMIN).asSequence().map { SimpleGrantedAuthority("ROLE_$it") }.toMutableList()
            )
    }

}
