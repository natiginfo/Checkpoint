import org.gradle.jvm.tasks.Jar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.41"
    id("org.jetbrains.dokka") version "0.10.0"
    `maven-publish`
    signing
}

group = "com.natigbabayev.checkpoint"
version = "0.1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.5.2")
    testImplementation("io.mockk:mockk:1.9.3")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

// Configure existing Dokka task to output HTML to typical Javadoc directory
tasks.dokka {
    outputFormat = "html"
    outputDirectory = "$buildDir/javadoc"
}

// Create dokka Jar task from dokka task output
val dokkaJar by tasks.creating(Jar::class) {
    group = JavaBasePlugin.DOCUMENTATION_GROUP
    description = "Assembles Kotlin docs with Dokka"
    archiveClassifier.set("javadoc")
    from(tasks.dokka)
}

// Create sources Jar from main kotlin sources
val sourcesJar by tasks.creating(Jar::class) {
    group = JavaBasePlugin.DOCUMENTATION_GROUP
    description = "Assembles sources JAR"
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

val OSSRH_USERNAME: String by project
val OSSRH_PASSWORD: String by project

publishing {
    repositories {
        maven {
            name = "MavenCentral"
            val releasesRepoUrl = "https://oss.sonatype.org/service/local/staging/deploy/maven2"
            val snapshotsRepoUrl = "https://oss.sonatype.org/content/repositories/snapshots"
            url = uri(if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl)
            credentials {
                username = OSSRH_USERNAME
                password = OSSRH_PASSWORD
            }
        }
    }
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            artifact(tasks["sourcesJar"])
            artifact(tasks["dokkaJar"])

            pom {
                name.set("Checkpoint")
                description.set("Validation library for Kotlin projects")
                url.set("https://github.com/natiginfo/Checkpoint")
                licenses {
                    license {
                        name.set("Apache License 2.0")
                        url.set("https://github.com/natiginfo/Checkpoint/blob/master/LICENSE")
                    }
                }
                developers {
                    developer {
                        id.set("natiginfo")
                        name.set("Natig Babayev")
                        email.set("hello@natigbabayev.com")
                    }
                }
                scm {
                    connection.set("scm:git:https://github.com/natiginfo/Checkpoint.git")
                    developerConnection.set("scm:git:https://github.com/natiginfo/Checkpoint.git")
                    url.set("https://natigbabayev.com")
                }

            }
        }
    }
}

signing {
    val PGP_SIGNING_KEY: String? by project
    val PGP_SIGNING_PASSWORD: String? by project
    useInMemoryPgpKeys(PGP_SIGNING_KEY, PGP_SIGNING_PASSWORD)
    sign(configurations.archives.get())
}
