package com.tastygear.core.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import java.util.*
import javax.annotation.PostConstruct
import javax.servlet.http.HttpServletRequest

@Component
class JwtProvider(
    val userService: UserDetailsService,
) {
    @Value("\${security.jwt.token.secret-key:secret}")
    var secretKey: String = "secret"

    @Value("\${security.jwt.token.access.expire-length:720000000}")
    val accessTokenExpiredTime: Long = 720000000

    @Value("\${security.jwt.token.password.reset.expire-length:300000}")
    val passwordResetTokenExpiredTime: Long = 300000

    @PostConstruct
    fun init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.toByteArray())
    }

    fun createAccessToken(username: String, roles: List<String>): String {
        if (username.isEmpty()) throw IllegalAccessException("Should be username isn't empty")
        return createToken(getClaims(username, roles), accessTokenExpiredTime)
    }

    fun createToken(claims: Claims, validityInMilliseconds: Long): String {
        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(Date())
            .setExpiration(Date(Date().time + validityInMilliseconds))
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact()
    }

    fun getClaims(username: String, roles: List<String>): Claims {
        return Jwts.claims()
            .setSubject(username).apply {
                this["roles"] = roles
            }
    }

    fun getAuthentication(token: String): Authentication? {
        val userDetails = userService.loadUserByUsername(getUsername(token))
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    fun getUsername(token: String): String {
        return Jwts.parser().setSigningKey(secretKey)
            .parseClaimsJws(token)
            .body
            .subject
    }

    fun resolve(request: HttpServletRequest): String {
        val bearerToken = request.getHeader("Authorization")
        return when (bearerToken.isNullOrBlank()) {
            true -> ""
            false -> {
                val startsWith = bearerToken.startsWith("Bearer ")
                if (startsWith) bearerToken.substring(7, bearerToken.length)
                else ""
            }
        }
    }

    fun validate(token: String?): Boolean {
        if (token == null || token.isEmpty() || token == "null") return false

        return try {
            !Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .body
                .expiration
                .before(Date())
        } catch (e: Exception) {
            false
        }
    }
}