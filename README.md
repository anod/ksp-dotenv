# ksp-dotenv
Gradle KSP Multiplatform plugin to generate code from .env file

# Example

`./gradlew example:run`

# Setup

1. Include [com.google.devtools.ksp](https://kotlinlang.org/docs/ksp-quickstart.html) in case it wasn't in the project yet
2. Add GitHup package repository following instructions https://github.com/anod/ksp-dotenv/packages/1921207
3. Add dependencies to build.gradle.kts
    ```kotlin
    dependencies {
        add("kspCommonMainMetadata", "info.anodsplace.dotenv:ksp-dotenv-jvm:$kspDotenvVersion")
        add("kspJvm", project("info.anodsplace.dotenv:ksp-dotenv-jvm:$kspDotenvVersion"))
    }
    ```
4. Configure the plugin inside  build.gradle.kts
   ````
    ksp {
        arg("info.anodsplace.dotenv.path", project.rootDir.toString())
        arg("info.anodsplace.dotenv.filename", "env.example") // default ".env"
        arg("info.anodsplace.dotenv.allowedKeys", "ENDPOINT*;ENV") // list separated by ';', supports '*','?' pattern
        arg("info.anodsplace.dotenv.camelCase", "true")
        arg("info.anodsplace.dotenv.package", "info.anodsplace.dotenv.generated")
        arg("info.anodsplace.dotenv.class", "DotEnvExample")
    }
    ```
5. Build & use generated file
   ```kotlin
    println("[ksp-dotenv]   ENDPOINT_FE=${DotEnvExample.endpointFe}")
    println("[ksp-dotenv]   ENDPOINT_BE=${DotEnvExample.endpointBe}")
    println("[ksp-dotenv]   ENV=${DotEnvExample.env}")
   ```
   
# Publish

   `./gradlew publish`

# Author

Alexandr Gavrishev, 2023
