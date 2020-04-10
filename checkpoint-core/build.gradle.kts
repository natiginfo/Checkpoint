import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    api(project(":checkpoint-core-abstraction"))
}

apply(from = "../gradle/gradle-test-dependencies.gradle.kts")

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

apply(from = "../gradle/gradle-mvn-push.gradle.kts")