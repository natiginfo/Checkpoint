configure<PublishingExtension> {
    val ossrhUsername: String? by project
    val ossrhPassword: String? by project
    repositories {
        maven {
            name = "MavenCentral"
            val releasesRepoUrl = "https://oss.sonatype.org/service/local/staging/deploy/maven2"
            val snapshotsRepoUrl = "https://oss.sonatype.org/content/repositories/snapshots"
            url = uri(if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl)
            credentials {
                username = ossrhUsername
                password = ossrhPassword
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

configure<SigningExtension> {
    val signingKey: String? by project
    val signingPassword: String? by project
    useInMemoryPgpKeys(signingKey, signingPassword)
    configure<PublishingExtension> {
        sign(publications)
    }
}
