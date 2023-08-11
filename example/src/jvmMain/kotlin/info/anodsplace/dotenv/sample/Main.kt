package info.anodsplace.dotenv.sample

import info.anodsplace.dotenv.generated.DotEnvExample

fun main() {
    println("[ksp-dotenv] Example of generated dotenv file:")

    println("[ksp-dotenv]   ENDPOINT_FE=${DotEnvExample.endpointFe}")
    println("[ksp-dotenv]   ENDPOINT_BE=${DotEnvExample.endpointBe}")
    println("[ksp-dotenv]   ENV=${DotEnvExample.env}")
}