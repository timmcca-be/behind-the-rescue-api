package com.adoptastray.behindtherescue

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication(exclude = [UserDetailsServiceAutoConfiguration::class])
@EnableCaching
class BehindTheRescueApplication

fun main(args: Array<String>) {
	runApplication<BehindTheRescueApplication>(*args)
}
