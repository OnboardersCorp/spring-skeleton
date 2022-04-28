package com.tastygear.external.api.security

import com.tastygear.core.security.JwtConfigurer
import com.tastygear.core.security.JwtProvider
import com.tastygear.rds.entity.user.Role
import com.tastygear.external.api.security.SecurityRole
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
class SecurityConfig(
    private val jwtProvider: JwtProvider,
) : WebSecurityConfigurerAdapter() {

    @Bean
    fun passwordEncoder(): PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    override fun configure(http: HttpSecurity) {
        //@formatter:off
        http.httpBasic().disable()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().headers().frameOptions().sameOrigin()
            .and().authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            .and().authorizeRequests()
            .antMatchers(HttpMethod.GET, *SecurityRole.NONE.patterns(HttpMethod.GET)).permitAll()
            .antMatchers(HttpMethod.POST, *SecurityRole.NONE.patterns(HttpMethod.POST)).permitAll()
            .and().authorizeRequests()

            .antMatchers(HttpMethod.GET, *SecurityRole.USER.patterns(HttpMethod.GET))
            .hasAnyRole(Role.USER.name)
            .antMatchers(HttpMethod.POST, *SecurityRole.USER.patterns(HttpMethod.POST))
            .hasAnyRole(Role.USER.name)
            .antMatchers(HttpMethod.PUT, *SecurityRole.USER.patterns(HttpMethod.PUT))
            .hasAnyRole(Role.USER.name)
            .antMatchers(HttpMethod.DELETE, *SecurityRole.USER.patterns(HttpMethod.DELETE))
            .hasAnyRole(Role.USER.name)

            .and().apply(JwtConfigurer(jwtProvider))
            .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS).sessionFixation().none()
            .and().cors()
        //@formatter:on
    }

}