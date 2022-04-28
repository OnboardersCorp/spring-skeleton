package com.tastygear.core.config

import com.tastygear.rds.entity.account.Account
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import java.util.*

@Configuration
@EnableJpaAuditing
internal class DataJpaConfig {

    @Bean
    fun auditorWare(): AuditorAware<Account> {
        return AuditorAware {
            val authentication = SecurityContextHolder.getContext().authentication

            when {
                invalidAuditor(authentication) -> Optional.empty()
                else -> Optional.of(authentication.principal as Account)
            }
        }
    }

    private fun invalidAuditor(authentication: Authentication?): Boolean {
        return authentication == null ||
                !authentication.isAuthenticated ||
                authentication is AnonymousAuthenticationToken
    }
}

