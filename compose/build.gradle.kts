import org.gradle.api.publish.maven.MavenPublication

plugins {
    alias(libs.plugins.compose)
    id("org.jetbrains.kotlin.jvm")
    id("maven-publish")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(compose.material)
    implementation(compose.material3)
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.github.The-Streamliners"
            artifactId = "compose"
            version = "1.0"

            afterEvaluate {
                from(components["java"])
            }
        }
    }
}