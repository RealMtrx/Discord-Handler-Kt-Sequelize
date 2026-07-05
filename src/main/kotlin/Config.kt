import io.github.cdimascio.dotenv.Dotenv
import io.github.cdimascio.dotenv.MissingEnvVariableException

data class Config(
    val token: String,
    val clientId: String,
    val botName: String,
    val prefix: String,
    val ownerIds: List<String>,
    val dbDialect: String,
    val dbUrl: String,
    val dbDriver: String,
    val dbUser: String,
    val dbPassword: String,
    val errorWebhook: String?,
    val slashCommandWebhook: String?,
    val prefixCommandWebhook: String?,
    val joinGuildWebhook: String?,
    val leaveGuildWebhook: String?,
    val readyWebhook: String?
) {
    companion object {
        fun load(): Config {
            val dotenv = Dotenv.configure().ignoreIfMalformed().ignoreIfMissing().load()
            return Config(
                token = safeGet(dotenv, "TOKEN") ?: "#",
                clientId = safeGet(dotenv, "CLIENT_ID") ?: "#",
                botName = safeGet(dotenv, "BOT_NAME") ?: "Discord Handler",
                prefix = safeGet(dotenv, "PREFIX") ?: "$",
                ownerIds = (safeGet(dotenv, "OWNER_IDS") ?: "#").split(",").map { it.trim() },
                dbDialect = safeGet(dotenv, "DB_DIALECT") ?: "POSTGRESQL",
                dbUrl = safeGet(dotenv, "DB_URL") ?: "jdbc:postgresql://localhost:5432/discord",
                dbDriver = safeGet(dotenv, "DB_DRIVER") ?: "org.postgresql.Driver",
                dbUser = safeGet(dotenv, "DB_USER") ?: "postgres",
                dbPassword = safeGet(dotenv, "DB_PASSWORD") ?: "postgres",
                errorWebhook = safeGet(dotenv, "ERROR_WEBHOOK"),
                slashCommandWebhook = safeGet(dotenv, "SLASH_COMMAND_WEBHOOK"),
                prefixCommandWebhook = safeGet(dotenv, "PREFIX_COMMAND_WEBHOOK"),
                joinGuildWebhook = safeGet(dotenv, "JOIN_GUILD_WEBHOOK"),
                leaveGuildWebhook = safeGet(dotenv, "LEAVE_GUILD_WEBHOOK"),
                readyWebhook = safeGet(dotenv, "READY_WEBHOOK")
            )
        }

        private fun safeGet(dotenv: Dotenv, key: String): String? {
            return try {
                val value = dotenv[key]
                if (value.isBlank()) null else value
            } catch (_: MissingEnvVariableException) {
                null
            }
        }
    }
}
