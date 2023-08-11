package info.anodsplace.dotenv

import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.*
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ksp.writeTo
import java.io.File

class DotEnvKspProcessor(
    environment: SymbolProcessorEnvironment,
) : SymbolProcessor {

    private var invoked = false
    private val codeGenerator = environment.codeGenerator
    private val options = Options(environment.options)
    private val logger: KSPLogger = DelegateLogger(environment.logger)
    private val parser = DotEnvParserFactory.create()

    override fun process(resolver: Resolver): List<KSAnnotated> {
        if (invoked) {
            return emptyList()
        }
        val file = resolveFile(options.path)
        logger.info("Resolved file: $file")
        val entries = parser.parse(file.absolutePath)
            .let { filterKeys(it) }
            .let { adjustKeyCase(it) }

        generateFile(entries = entries)
            .writeTo(codeGenerator, Dependencies(true))
        invoked = true
        return emptyList()
    }

    private fun filterKeys(entries: Collection<DotEnvEntry>): Collection<DotEnvEntry> {
        return if (options.allowedKeys.isEmpty()) {
            entries
        } else {
            entries.filter { entry -> options.allowedKeys.firstOrNull { pattern -> isMatch(entry.key, pattern) } != null }
        }
    }

    private fun adjustKeyCase(entries: Collection<DotEnvEntry>): Collection<DotEnvEntry> {
        return if (options.camelCase) {
            entries.map { it.copy(key = it.key.camelcase()) }
        } else {
            entries
        }
    }

    private fun resolveFile(configPath: String): File {
        val file = File(configPath)
        if (file.isFile) {
            return file
        } else if (file.isDirectory) {
            return File(file, Options.defaultFile)
        }
        return File(Options.defaultFile)
    }

    private fun generateFile(entries: Collection<DotEnvEntry>): FileSpec {
        val properties = entries.map { entry ->
            logger.info("  ${entry.key}=${entry.value}")
            PropertySpec.builder(entry.key, String::class)
                .mutable(mutable = false)
                .addModifiers(KModifier.PUBLIC)
                .initializer("%S", entry.value)
                .build()
        }

        return FileSpec.builder(options.packageName, options.className)
            .addType(
                TypeSpec.objectBuilder(options.className)
                    .addProperties(properties)
                .build()
            )
            .build()
    }
}

private class DelegateLogger(val delegate: KSPLogger) : KSPLogger {
    var hasError = false
        private set
    override fun error(message: String, symbol: KSNode?) {
        hasError = true
        delegate.error(prefix(message), symbol)
    }
    override fun exception(e: Throwable) {
        hasError = true
        delegate.exception(e)
    }

    override fun info(message: String, symbol: KSNode?) {
        delegate.info(prefix(message), symbol)
    }

    override fun logging(message: String, symbol: KSNode?) {
        delegate.logging(prefix(message), symbol)
    }

    override fun warn(message: String, symbol: KSNode?) {
        delegate.warn(prefix(message), symbol)
    }

    private fun prefix(message: String) = "[info.anodsplace.dotenv] $message"
}


