import io.github.cdimascio.dotenv.Dotenv

data class DotEnvKspEntry(val key: String, val value: String)

interface DotEnvKspParser {
    fun parse(path: String, filename: String): Collection<DotEnvKspEntry>
}

class LibDotEnvKspParser : DotEnvKspParser {
    override fun parse(path: String, filename: String): Collection<DotEnvKspEntry> {
        return Dotenv.configure()
            .directory(path)
            .filename(filename)
            .load()
            .entries(Dotenv.Filter.DECLARED_IN_ENV_FILE)
            .map { DotEnvKspEntry(it.key, it.value) }
    }
}

object DotEnvKspParserFactory {
    fun create(): DotEnvKspParser = LibDotEnvKspParser()
}