package com.tastygear.external.api.security

import org.springframework.http.HttpMethod

enum class SecurityRole(private val matchers: List<AuthRequest>) {
    NONE(
        listOf(
            AuthRequest(
                HttpMethod.GET, listOf(
                    "/v3/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/security",
                    "/swagger-ui.html", "/swagger-ui/*", "/webjars/**", "/v3/**",
                    "/account/**", "/banner/**", "/business/operator/**", "/chargingStation/**",
                    "/notice/**", "/region/**", "/charging/card/**", "/rate/**", "/reservation/available/**"
                )
            ),
            AuthRequest(
                HttpMethod.POST, listOf(
                    "/account/**"
                )
            )
        )
    ),
    USER(
        listOf(
            AuthRequest(
                HttpMethod.GET, listOf(
                    "**"
                )
            ),
            AuthRequest(
                HttpMethod.POST, listOf(
                    "**"
                )
            ),
            AuthRequest(
                HttpMethod.PUT, listOf(
                    "**"
                )
            ),
            AuthRequest(
                HttpMethod.DELETE, listOf(
                    "**"
                )
            )
        )
    );

    fun patterns(method: HttpMethod): Array<String> {
        return matchers.find { it.method == method }?.patterns?.toTypedArray() ?: arrayOf()
    }
}

data class AuthRequest(
    val method: HttpMethod,
    val patterns: List<String> = listOf(),
)