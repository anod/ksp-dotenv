# ksp-dotenv
Gradle KSP Multiplatform plugin to generate code from .env file

# Running example

`./gradlew example:run`

# Setup

1. Include "com.google.devtools.ksp" in case it's wasnt in the project yet
2. Update build.gradle.kts
    ```kotlin
    dependencies {
        add("kspCommonMainMetadata", "info.anodsplace.dotenv")
        add("kspJvm", project("info.anodsplace.dotenv"))
    }
    
    ksp {
        arg("info.anodsplace.dotenv.path", project.rootDir.toString())
        arg("info.anodsplace.dotenv.filename", "env.example") // default ".env"
        arg("info.anodsplace.dotenv.allowedKeys", "ENDPOINT*;ENV") // list separated by ';', supports '*','?' pattern
        arg("info.anodsplace.dotenv.camelCase", "true")
        arg("info.anodsplace.dotenv.package", "info.anodsplace.dotenv.generated")
        arg("info.anodsplace.dotenv.class", "DotEnvExample")
    }
    ```
3. Use generated file
   ```kotlin
    println("[ksp-dotenv]   ENDPOINT_FE=${DotEnvExample.endpointFe}")
    println("[ksp-dotenv]   ENDPOINT_BE=${DotEnvExample.endpointBe}")
    println("[ksp-dotenv]   ENV=${DotEnvExample.env}")
   ```