package handlers

data class StartupData(
    val name: String,
    val prefix: Int,
    val slash: Int,
    val events: Int,
    val anticrash: Boolean,
    val database: Boolean
)

object Logger {
    fun startupReport(data: StartupData) {
        val line = "\u001B[36m\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u001B[0m"

        println()
        println(line)
        println("  \u001B[1;36m${data.name}\u001B[0m")
        println(line)
        println("  ${statusEmoji(data.slash > 0)} Slash Commands: ${data.slash}")
        println("  ${statusEmoji(data.prefix > 0)} Prefix Commands: ${data.prefix}")
        println("  ${statusEmoji(data.events > 0)} Events Loaded: ${data.events}")
        println("  ${statusEmoji(data.anticrash)} AntiCrash: ${if (data.anticrash) "Active" else "Inactive"}")
        println("  ${statusEmoji(data.database)} Database: ${if (data.database) "Connected" else "Disconnected"}")
        println(line)
        println("  \u001B[35mBot is now online and fully operational.\u001B[0m")
        println()
    }

    private fun statusEmoji(ok: Boolean): String {
        return if (ok) "\u2705" else "\u274C"
    }
}
