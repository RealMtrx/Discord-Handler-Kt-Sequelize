package handlers

import commands.prefix.public.PingCommand
import core.Emojis
import net.dv8tion.jda.api.entities.Message

object PrefixHandler {
    private val prefixCommands = mapOf(
        PingCommand.name to PingCommand,
        "p" to PingCommand
    )

    suspend fun execute(message: Message, prefix: String) {
        val author = message.author ?: return
        if (author.isBot) return

        val content = message.contentRaw
        if (!content.startsWith(prefix)) return

        val args = content.removePrefix(prefix).trim().split(Regex("\\s+"))
        val commandName = args.firstOrNull()?.lowercase() ?: return
        val commandArgs = args.drop(1)

        val command = prefixCommands[commandName] ?: return

        try {
            command.execute(message, commandArgs)
        } catch (e: Exception) {
            println("\u001B[31m[PrefixHandler] Error in $commandName: ${e.message}\u001B[0m")
        }
    }
}
