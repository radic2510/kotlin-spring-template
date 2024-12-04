package com.yobi.standard.config

import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import redis.embedded.RedisServer
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

@Profile("local", "test")
@Configuration("redisEmbeddedConfig")
class RedisEmbeddedConfig {

    @Value("\${spring.data.redis.port}")
    private var redisPort: Int = 16379

    private var redisServer: RedisServer? = null

    @PostConstruct
    @Throws(IOException::class)
    private fun start() {
        val port = if (isRedisRunning()) findAvailablePort() else redisPort
        redisServer = RedisServer(port)
        redisServer?.start()
    }

    @PreDestroy
    @Throws(IOException::class)
    private fun stop() {
        redisServer?.stop()
    }

    @Throws(IOException::class)
    private fun isRedisRunning(): Boolean {
        return isRunning(executeGrepProcessCommand(redisPort))
    }

    @Throws(IOException::class)
    fun findAvailablePort(): Int {
        for (port in 10000..65535) {
            val process = executeGrepProcessCommand(port)
            if (!isRunning(process)) {
                return port
            }
        }
        throw IllegalArgumentException("Not Found Available port: 10000 ~ 65535")
    }

    @Throws(IOException::class)
    private fun executeGrepProcessCommand(port: Int): Process {
        val command = "netstat -nat | grep LISTEN|grep $port"
        val shell = arrayOf("/bin/sh", "-c", command)
        return Runtime.getRuntime().exec(shell)
    }

    private fun isRunning(process: Process): Boolean {
        val pidInfo = StringBuilder()
        try {
            BufferedReader(InputStreamReader(process.inputStream)).use { input ->
                var line: String?
                while (input.readLine().also { line = it } != null) {
                    pidInfo.append(line)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return pidInfo.isNotEmpty()
    }
}