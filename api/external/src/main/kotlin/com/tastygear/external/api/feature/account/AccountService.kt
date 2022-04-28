package com.tastygear.external.api.feature.account

import com.tastygear.core.dto.account.AccountDto
import com.tastygear.core.security.JwtProvider
import com.tastygear.rds.entity.user.Role
import com.tastygear.rds.entity.user.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AccountService(
    val userRepository: UserRepository,
    val passwordEncoder: PasswordEncoder,
    val jwtProvider: JwtProvider,
) {
    fun signUp(requestDto: AccountDto.SignUpRequest): AccountDto.SignUpResponse {
        return userRepository.save(requestDto.map(passwordEncoder))
            .run { createAccessTokenById(this.username) }
            .run(AccountDto::SignUpResponse)
    }

    private fun createAccessTokenById(username: String) =
        jwtProvider.createAccessToken(username, listOf(Role.USER.name))
}