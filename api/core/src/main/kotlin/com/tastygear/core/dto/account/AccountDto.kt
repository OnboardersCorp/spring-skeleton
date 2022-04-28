package com.tastygear.core.dto.account

import com.tastygear.rds.entity.user.User
import org.springframework.security.crypto.password.PasswordEncoder
import javax.validation.constraints.NotBlank

class AccountDto {

    data class SignUpRequest(
        @NotBlank
        val name: String,

        val username: String,

        @NotBlank
        val password: String,

        val gender: User.Gender = User.Gender.NONE,

        @NotBlank
        val region: String,

        ) {
        fun map(passwordEncoder: PasswordEncoder): User {
            return User(
                name = name,
                gender = gender,
                region = region
            ).apply {
                username = this@SignUpRequest.username
                password = passwordEncoder.encode(this@SignUpRequest.password)
            }
        }
    }

    data class SignUpResponse(
        val token: String = ""
    )

}