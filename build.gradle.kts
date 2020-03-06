plugins {
    id("idea")
    id("org.springframework.boot") version "2.2.1.RELEASE"
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
    kotlin("jvm") version "1.3.61"
    kotlin("plugin.spring") version "1.3.61"
}

group = "com.github.dkurata38"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework:spring-webmvc")
}
