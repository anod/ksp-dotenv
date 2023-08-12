val kspVersion: String by project

plugins {
    kotlin("multiplatform")
    id("maven-publish")
}

group = "info.anodsplace.dotenv"
version = "0.1.0"

kotlin {
    jvm()

    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation("io.github.cdimascio:dotenv-kotlin:6.4.1")
                implementation("com.squareup:kotlinpoet:1.14.2")
                implementation("com.squareup:kotlinpoet-ksp:1.14.2")
                implementation("com.google.devtools.ksp:symbol-processing-api:$kspVersion")
            }
            kotlin.srcDir("src/main/kotlin")
            resources.srcDir("src/main/resources")
        }
    }
    val publicationsFromMainHost = listOf(jvm()).map { it.name } + "kotlinMultiplatform"
    publishing {
        publications {
            matching { it.name in publicationsFromMainHost }.all {
                val targetPublication = this@all
                tasks.withType<AbstractPublishToMaven>()
                    .matching { it.publication == targetPublication }
                    .configureEach { onlyIf { findProperty("isMainHost") == "true" } }
            }
        }
    }
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/anod/ksp-dotenv")
            credentials {
                username = project.findProperty("GITHUB_USER") as? String ?: System.getenv("GITHUB_USER")
                password = project.findProperty("GITHUB_TOKEN") as? String ?: System.getenv("GITHUB_TOKEN")
            }
        }
    }
    publications {
        register<MavenPublication>("github") {
            groupId = project.group.toString()
            artifactId = project.name
            version = project.version.toString()
            pom {
                description.set(
                    "Gradle KSP Multiplatform plugin to generate code from .env file")
                name.set(project.name)
                url.set("https://github.com/anod/ksp-dotenv.git")
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://github.com/anod/ksp-dotenv/blob/main/LICENSE")
                        distribution.set("repo")
                    }
                }
                developers {
                    developer {
                        id.set("anodsplace.info")
                        name.set("Alexandr Gavrishev")
                        email.set("alex.gavrishev@gmail.com")
                    }
                }
                scm { url.set("scm:git:git@github.com:facebook/yoga.git") }
            }
        }
    }
}