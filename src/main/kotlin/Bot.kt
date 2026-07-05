import database.Database
import handlers.*
import events.*
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.interactions.commands.build.Commands
import net.dv8tion.jda.api.requests.GatewayIntent

class Bot(val config: Config) {
    lateinit var jda: net.dv8tion.jda.api.JDA
        private set

    suspend fun start() {
        println("\u001B[36m\u2554\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2557\u001B[0m")
        println("\u001B[36m\u2551     Starting Discord Handler     \u2551\u001B[0m")
        println("\u001B[36m\u255A\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u255D\u001B[0m")
        println()

        println("\u001B[34m[System] Initializing AntiCrash...\u001B[0m")
        AntiCrash.init(config)

        println("\u001B[34m[System] Connecting to Database...\u001B[0m")
        val dbConnected = Database.connect(config)

        println("\u001B[34m[System] Connecting to Discord...\u001B[0m")
        jda = JDABuilder.createDefault(config.token)
            .enableIntents(GatewayIntent.MESSAGE_CONTENT)
            .setActivity(Activity.playing(config.botName))
            .build()

        registerEvents()
        registerSlashCommands()

        Logger.startupReport(
            StartupData(
                name = config.botName,
                prefix = 1,
                slash = 1,
                events = 5,
                anticrash = true,
                database = dbConnected
            )
        )

        jda.awaitReady()
    }

    private fun registerEvents() {
        val eventHandler = EventHandler(this)
        jda.addEventListener(eventHandler)
        println("\u001B[32m[System] Events registered (5)\u001B[0m")
    }

    private fun registerSlashCommands() {
        try {
            jda.updateCommands().addCommands(
                Commands.slash("ping", "Replies with Pong!")
            ).queue()
            println("\u001B[32m[System] Slash commands registered (1)\u001B[0m")
        } catch (e: Exception) {
            println("\u001B[31m[System] Failed to register slash commands: ${e.message}\u001B[0m")
            AntiCrash.reportError("Slash Command Registration", e.message ?: "Unknown error")
        }
    }
}
