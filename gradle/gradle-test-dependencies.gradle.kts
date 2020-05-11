val implementation by configurations
val testImplementation by configurations

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.5.2")
    testImplementation("io.mockk:mockk:1.9.3")
}

tasks.withType<org.gradle.api.tasks.testing.Test> {
    useJUnitPlatform()
    finalizedBy(tasks.withType<org.gradle.testing.jacoco.tasks.JacocoReport>())
}

tasks.withType<org.gradle.testing.jacoco.tasks.JacocoReport> {
    reports {
        xml.isEnabled = true
        csv.isEnabled = false
        xml.destination = file("${buildDir}/reports/jacoco/report.xml")
    }

    dependsOn(tasks.withType<org.gradle.api.tasks.testing.Test>())
}
