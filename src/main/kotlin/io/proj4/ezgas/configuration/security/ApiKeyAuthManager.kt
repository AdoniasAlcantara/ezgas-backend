package io.proj4.ezgas.configuration.security

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.Authentication

class ApiKeyAuthManager(private val secret: String) : AuthenticationManager {

    override fun authenticate(auth: Authentication): Authentication {
        if (auth.principal != secret) {
            throw BadCredentialsException("Access denied")
        }

        return auth.also { it.isAuthenticated = true }
    }
}
