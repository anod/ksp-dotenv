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
        val jvmMain by getting
    }
}

application {
    mainClass.set("info.anodsplace.dotenv.sample.MainKt")
}

dependencies {
    add("kspCommonMainMetadata", project(":plugin"))
    add("kspJvm", project(":plugin"))
}

ksp {
    arg("info.anodsplace.dotenv.path", File(project.rootDir, "env.example").toString())
    arg("info.anodsplace.dotenv.allowedKeys", "ENDPOINT*;ENV")
    arg("info.anodsplace.dotenv.camelCase", "true")
    arg("info.anodsplace.dotenv.package", "info.anodsplace.subscriptions")
    arg("info.anodsplace.dotenv.class", "DotEnvClient")
}