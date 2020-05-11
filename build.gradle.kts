group = "com.natigbabayev.checkpoint"

allprojects {
    group = "com.natigbabayev.checkpoint"
}
plugins {
    kotlin("jvm") version "1.3.41"
    id("org.jetbrains.dokka") version "0.10.0"
    `maven-publish`
    signing
    jacoco
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.dokka")
    apply(plugin = "org.gradle.maven-publish")
    apply(plugin = "org.gradle.signing")
    apply(plugin = "org.gradle.jacoco")

    // Configure existing Dokka task to output HTML to typical Javadoc directory
    tasks.dokka {
        outputFormat = "html"
        outputDirectory = "$buildDir/javadoc"
    }

    // Create dokka Jar task from dokka task output
    val dokkaJar by tasks.creating(org.gradle.jvm.tasks.Jar::class) {
        group = JavaBasePlugin.DOCUMENTATION_GROUP
        description = "Assembles Kotlin docs with Dokka"
        archiveClassifier.set("javadoc")
        from(tasks.dokka)
    }

    // Create sources Jar from main kotlin sources
    val sourcesJar by tasks.creating(org.gradle.jvm.tasks.Jar::class) {
        group = JavaBasePlugin.DOCUMENTATION_GROUP
        description = "Assembles sources JAR"
        archiveClassifier.set("sources")
        from(sourceSets.main.get().allSource)
    }
}

allprojects {
    repositories {
        mavenCentral()
        jcenter()
    }
}

jacoco {
    toolVersion = "0.8.5"
}
