import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    api(project(":checkpoint-core-abstraction"))
    implementation("io.reactivex.rxjava2:rxjava:2.2.19")
}

apply(from = "../gradle/gradle-test-dependencies.gradle.kts")

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

apply(from = "../gradle/gradle-mvn-push.gradle.kts")
