package info.anodsplace.dotenv

import io.github.cdimascio.dotenv.Dotenv

data class DotEnvEntry(val key: String, val value: String)

interface DotEnvKspParser {
    fun parse(path: String): Collection<DotEnvEntry>
}

class JvmDotEnvKspParser : DotEnvKspParser {
    override fun parse(path: String): Collection<DotEnvEntry> {
        return Dotenv.configure()
            .directory(path)
            .load()
            .entries(Dotenv.Filter.DECLARED_IN_ENV_FILE)
            .map { DotEnvEntry(it.key, it.value) }
    }
}

object DotEnvParserFactory {
    fun create(): DotEnvKspParser = JvmDotEnvKspParser()
}