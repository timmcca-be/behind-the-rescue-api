package com.adoptastray.behindtherescue.web.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer




@Configuration
class CrossOriginConfiguration {
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
}