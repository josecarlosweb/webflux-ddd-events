package com.greenmile

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class WebfluxDddEventsApplication

fun main(args: Array<String>) {
    runApplication<WebfluxDddEventsApplication>(*args)
}
