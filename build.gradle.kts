plugins {
    java
    id("org.springframework.boot") version "3.2.3"
    id("io.spring.dependency-management") version "1.1.4"
    id("com.epages.restdocs-api-spec") version "0.17.1"
    id("jacoco")
}

group = "com.cdd"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

jacoco {
    toolVersion = "0.8.11"
    reportsDirectory.set(layout.buildDirectory.dir("reports/jacoco"))
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}


extra["springCloudVersion"] = "2023.0.0"
val sonarqubeVersion = "3.3"
val openapiUiVersion = "1.7.0"
val openapi3GeneratorVersion = "0.17.1"
val restdocsVersion = "3.0.0"

dependencies {
    /* Spring Boot  */
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-redis-reactive")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    /* Spring Cloud */
    implementation("org.springframework.cloud:spring-cloud-starter")
    implementation("org.springframework.cloud:spring-cloud-starter-bootstrap")
    implementation("org.springframework.cloud:spring-cloud-starter-bus-amqp")
    implementation("org.springframework.cloud:spring-cloud-starter-circuitbreaker-resilience4j")
    implementation("org.springframework.cloud:spring-cloud-starter-config")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
    /* DataBase */
    runtimeOnly("com.h2database:h2")
    runtimeOnly("com.mysql:mysql-connector-j")
    /* Kafka */
    implementation("org.springframework.kafka:spring-kafka")
    testImplementation("org.springframework.kafka:spring-kafka-test")
    /* QueryDSL */
    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
    annotationProcessor("com.querydsl:querydsl-apt:5.0.0:jakarta")
    annotationProcessor("jakarta.annotation:jakarta.annotation-api")
    annotationProcessor("jakarta.persistence:jakarta.persistence-api")
    /* QueryDSL - Spatial */
    implementation("org.hibernate.orm:hibernate-spatial:6.4.4.Final")
    implementation("com.querydsl:querydsl-sql-spatial")
    /* Flyway */
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-mysql")
    /* Lombok */
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    /* Logging - ZipKin */
    implementation("io.micrometer:micrometer-tracing-bridge-brave")
    implementation("io.zipkin.reporter2:zipkin-reporter-brave")
    /* Test */
    implementation("org.sonarsource.scanner.gradle:sonarqube-gradle-plugin:$sonarqubeVersion")
    testImplementation("org.mockito:mockito-core")
    /* Sangchu */
    implementation("com.github.CoffeeDrivenDevelopment:sangchu-common:0.0.3")
    implementation("com.github.CoffeeDrivenDevelopment:sangchu-passport:0.0.8")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.2")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

/* QueryDSL Start */
tasks.named("clean") {
    doLast {
        file("src/main/generated").deleteRecursively()
    }
}/* QueryDSL End */

/* Jacoco Start */
tasks.jacocoTestReport {
    dependsOn(":copyOasToSwagger")
    reports {
        html.required = true
        html.outputLocation = layout.buildDirectory.dir("reports/jacoco/index.xml")
    }


    classDirectories.setFrom(
        files(classDirectories.files.map {
            fileTree(it).apply {
                exclude(
                    "**/*Application*",
                    "**/*Exception*",
                    "**/BaseEntity*",
                    "**/ControllerAdvice*",
                    "**/dto/**",
                    "**/cond/**",
                )
            }
        })
    )


    finalizedBy(tasks.jacocoTestCoverageVerification)
}

tasks.jacocoTestCoverageVerification {
    dependsOn(":copyOasToSwagger")
    violationRules {
        rule {
            enabled = true
            element = "CLASS"

            limit {
                counter = "LINE"
                value = "COVEREDRATIO"
                minimum = BigDecimal.valueOf(0)
            }

            excludes = listOf(
                "**/*Application*",
                "**/*Exception*",
                "**/BaseEntity*",
                "**/ControllerAdvice*",
                "**/dto/**",
                "**/cond/**",
            )
        }
    }
}
/* Jacoco End */