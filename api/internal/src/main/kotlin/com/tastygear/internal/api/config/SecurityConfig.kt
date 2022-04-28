package com.tastygear.internal.api.config

import com.tastygear.core.security.JwtProvider
import com.tastygear.internal.api.security.SecurityRole
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource


@Configuration
class SecurityConfig(
    val jwtProvider: JwtProvider
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
            .cors().and()
            .csrf().disable()
            .formLogin().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().headers().frameOptions().sameOrigin()
            .and().authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            .and().authorizeRequests()
            .antMatchers(HttpMethod.GET, *SecurityRole.NONE.patterns(HttpMethod.GET)).permitAll()
            .antMatchers(HttpMethod.POST, *SecurityRole.NONE.patterns(HttpMethod.POST)).permitAll()

            .and().authorizeRequests()
            .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS).sessionFixation().none()
            .and()
        //@formatter:on
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        return UrlBasedCorsConfigurationSource().apply {
            registerCorsConfiguration("/**", CorsConfiguration().apply {
                addAllowedHeader("*")
                addAllowedMethod("*")
                addAllowedOriginPattern("*")
                allowCredentials = true
            })
        }
    }

}