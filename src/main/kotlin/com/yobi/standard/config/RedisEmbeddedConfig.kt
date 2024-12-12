package com.yobi.standard.config

import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import redis.embedded.RedisServer
import java.io.IOException
import java.net.ServerSocket

@Profile("local")
@Configuration("redisEmbeddedConfig")
class RedisEmbeddedConfig {

    @Value("\${spring.data.redis.port}")
    private var redisPort: Int = 16379

    private var redisServer: RedisServer? = null

    @PostConstruct
    private fun start() {
        val port = if (isPortInUse(redisPort)) findAvailablePort() else redisPort
        println("Attempting to start Embedded Redis on port: $port")
        redisServer = RedisServer(port)
        redisServer?.start()

        if (redisServer?.isActive == true) {
            println("Embedded Redis started successfully on port: $port")
        } else {
            throw IllegalStateException("Failed to start Embedded Redis on port: $port")
        }
    }

    @PreDestroy
    @Throws(IOException::class)
    private fun stop() {
        redisServer?.stop()
        println("Embedded Redis stopped")
    }

    fun findAvailablePort(): Int {
        for (port in 10000..65535) {
            if (!isPortInUse(port)) {
                return port
            }
        }
        throw IllegalArgumentException("No available port found in range: 10000 ~ 65535")
    }

    private fun isPortInUse(port: Int): Boolean {
        return try {
            ServerSocket(port).use { false }
        } catch (_: IOException) {
            true
        }
    }
}