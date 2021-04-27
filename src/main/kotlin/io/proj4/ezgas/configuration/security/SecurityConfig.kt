package io.proj4.ezgas.configuration.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy.STATELESS
import org.springframework.security.config.web.servlet.invoke
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@EnableWebSecurity
class SecurityConfig(
        @Value("\${ezgas.api.key}")
        private val key: String,

        @Value("\${ezgas.api.secret}")
        private val secret: String
) : WebSecurityConfigurerAdapter() {

    private val authManager = ApiKeyAuthManager(secret)

    override fun authenticationManager() = authManager

    override fun configure(http: HttpSecurity) {
        val authFilter = ApiKeyAuthFilter(key, authManager)

        http {
            csrf { disable() }

            sessionManagement { sessionCreationPolicy = STATELESS }

            authorizeRequests { authorize(anyRequest, authenticated) }

            addFilterAt(authFilter, UsernamePasswordAuthenticationFilter::class.java)
        }
    }
}
