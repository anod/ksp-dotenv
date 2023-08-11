plugins {
    kotlin("multiplatform")
    id("maven-publish")
}

val kspVersion: String by project

group = "info.anodsplace.dotenv"
version = "0.1.0"

kotlin {
    jvm()
    
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation("com.squareup:kotlinpoet:1.14.2")
                implementation("com.squareup:kotlinpoet-ksp:1.14.2")
                implementation("com.google.devtools.ksp:symbol-processing-api:$kspVersion")
                implementation("io.github.cdimascio:dotenv-kotlin:6.4.1")
            }
            kotlin.srcDir("src/jvmMain/kotlin")
            resources.srcDir("src/jvmMain/resources")
        }
    }
}
