package com.yobi.standard

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class StandardApplication

fun main(args: Array<String>) {
	runApplication<StandardApplication>(*args)
}
