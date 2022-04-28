package com.tastygear.internal.api.security

import org.springframework.http.HttpMethod

enum class SecurityRole(private val matchers: List<AuthRequest>) {
    NONE(
        listOf(
            AuthRequest(
                HttpMethod.GET, listOf(
                    "/**"
                )
            ),
            AuthRequest(
                HttpMethod.POST, listOf(
                    "/**"
                )
            )
        )
    ),
    ADMIN(
        listOf(
            AuthRequest(
                HttpMethod.GET, listOf(
                    "/**"
                )
            ),
            AuthRequest(
                HttpMethod.POST, listOf(
                    "/**"
                )
            ),
            AuthRequest(
                HttpMethod.PUT, listOf(
                    "/**"
                )
            ),
            AuthRequest(
                HttpMethod.DELETE, listOf(
                    "/**"
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