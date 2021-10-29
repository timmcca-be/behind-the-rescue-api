package com.adoptastray.behindtherescue.web.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
class WebSecurityConfiguration : WebSecurityConfigurerAdapter() {
    @Value("\${behind-the-rescue.allowed-origins}")
    private lateinit var allowedOrigins: String;

    @Bean
    fun corsConfigurer(): WebMvcConfigurer = object : WebMvcConfigurer {
        override fun addCorsMappings(registry: CorsRegistry) {
            registry
                .addMapping("/**")
                .allowedOrigins(*allowedOrigins.split(",").toTypedArray())
        }
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.requiresChannel()
            .requestMatchers({ request -> request.getHeader("X-Forwarded-Proto") != null })
            .requiresSecure()
    }
}