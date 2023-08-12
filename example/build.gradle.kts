plugins {
    kotlin("multiplatform")
    application
    id("com.google.devtools.ksp")
}

group = "info.anodsplace.dotenv.sample"
version = "1.0-SNAPSHOT"

kotlin {
    jvm {
        withJava()
    }

    sourceSets {
        val jvmMain by getting {
            // make different package visible to IDE
            kotlin.srcDir("build/generated/ksp/jvm/jvmMain/kotlin")
        }
    }
}

application {
    mainClass.set("info.anodsplace.dotenv.sample.MainKt")
}

dependencies {
    add("kspCommonMainMetadata", project(":ksp-dotenv"))
    add("kspJvm", project(":ksp-dotenv"))
}

ksp {
    arg("info.anodsplace.dotenv.path", project.rootDir.toString())
    arg("info.anodsplace.dotenv.filename", "env.example") // default ".env"
    arg("info.anodsplace.dotenv.allowedKeys", "ENDPOINT*;ENV")
    arg("info.anodsplace.dotenv.camelCase", "true")
    arg("info.anodsplace.dotenv.package", "info.anodsplace.dotenv.generated")
    arg("info.anodsplace.dotenv.class", "DotEnvExample")
}