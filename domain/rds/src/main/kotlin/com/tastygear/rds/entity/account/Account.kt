package com.tastygear.rds.entity.account

import com.fasterxml.jackson.annotation.JsonIgnore
import com.tastygear.rds.entity.user.Role
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDateTime
import javax.persistence.*

@MappedSuperclass
abstract class Account(

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    open var id: String? = null,

    @Column(unique = true)
    private var username: String = "",

    @JsonIgnore
    private var password: String = "",

    open var enabled: Boolean = true,

    @Enumerated(EnumType.STRING)
    open var role: Role,

    @CreationTimestamp
    open var createdDate: LocalDateTime? = null,

    @UpdateTimestamp
    open var lastModifiedDate: LocalDateTime? = null,
) : UserDetails {

    override fun getAuthorities() =
        listOf(SimpleGrantedAuthority("ROLE_$role"))

    override fun getUsername() = username

    open fun setUsername(username: String) {
        this.username = username
    }

    override fun getPassword() = password

    open fun setPassword(password: String) {
        this.password = password
    }

    override fun isAccountNonExpired() = enabled

    override fun isAccountNonLocked() = enabled

    override fun isCredentialsNonExpired() = enabled

    override fun isEnabled() = enabled
}