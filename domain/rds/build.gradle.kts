import org.springframework.boot.gradle.tasks.bundling.BootJar

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = false
jar.enabled = true

plugins {
    id("org.springframework.boot")
    id("kotlin-kapt")

    kotlin("jvm")
    kotlin("plugin.spring")
}

dependencies {
    api(kotlin("reflect"))

    api("org.springframework.boot:spring-boot-starter-data-jpa")
    api("org.springframework.boot:spring-boot-starter-security")

    api("com.fasterxml.jackson.core:jackson-annotations:2.12.2")
    api("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.12.2")
    api("com.fasterxml.jackson.module:jackson-module-kotlin:2.12.2")

    runtimeOnly("com.h2database:h2:1.4.200")
    runtimeOnly("org.postgresql:postgresql:42.3.1")

}