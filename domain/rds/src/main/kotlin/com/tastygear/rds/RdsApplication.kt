package com.tastygear.rds

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RdsApplication

fun main(args: Array<String>) {
    runApplication<RdsApplication>(*args)
}
