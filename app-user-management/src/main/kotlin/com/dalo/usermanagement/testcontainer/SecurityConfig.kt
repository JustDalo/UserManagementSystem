package com.dalo.usermanagement.testcontainer

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.web.servlet.invoke
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager

@Configuration
@EnableWebSecurity
class SecurityConfig : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity?) {
        http {
            httpBasic {}
            csrf { disable() }
            authorizeRequests {
                authorize(HttpMethod.POST, "/api/users/", hasAnyAuthority("ADMIN", "USER"))
                authorize(HttpMethod.GET, "/api/users/", hasAnyAuthority("ADMIN"))
                authorize(HttpMethod.POST, "/api/users/", hasAnyAuthority("ADMIN", "USER"))
                authorize(HttpMethod.DELETE, "/api/users/", hasAnyAuthority("ADMIN"))
                authorize("/**", permitAll)
            }
        }
    }

    @Bean
    override fun userDetailsService(): UserDetailsService {
        return InMemoryUserDetailsManager(
            User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin"))
                .authorities("ADMIN")
                .build(),
            User.builder()
                .username("user")
                .password(passwordEncoder().encode("user"))
                .authorities("USER")
                .build()
        )
    }

    @Bean
    protected fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}