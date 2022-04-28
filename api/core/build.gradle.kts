import org.springframework.boot.gradle.tasks.bundling.BootJar

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = false
jar.enabled = true

plugins {
    id("org.springframework.boot")

    kotlin("jvm")
    kotlin("plugin.spring")
}

extra["springCloudVersion"] = "2020.0.4"

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

dependencies {
    api(project(":domain:rds"))

    api("org.springframework.boot:spring-boot-starter-validation")
    api("org.springframework.boot:spring-boot-starter-web")
    api("io.jsonwebtoken:jjwt:0.9.1")

    api("io.springfox:springfox-boot-starter:3.0.0")

    api("io.github.openfeign:feign-jackson:11.8")

    testImplementation("org.springframework.boot:spring-boot-starter-test")

}