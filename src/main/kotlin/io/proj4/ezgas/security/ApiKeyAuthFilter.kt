package io.proj4.ezgas.security

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter
import javax.servlet.http.HttpServletRequest

class ApiKeyAuthFilter(
        private val key: String,
        authManager: AuthenticationManager
) : AbstractPreAuthenticatedProcessingFilter() {

    init {
        setAuthenticationManager(authManager)
    }

    override fun getPreAuthenticatedCredentials(request: HttpServletRequest): String = "N/A"

    override fun getPreAuthenticatedPrincipal(request: HttpServletRequest): String? = request.getHeader(key)
}