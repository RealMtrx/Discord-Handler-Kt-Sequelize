package handlers

import commands.slash.public.PingCommand
import core.Emojis
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent

object CommandHandler {
    suspend fun execute(event: SlashCommandInteractionEvent) {
        val commandName = event.name

        try {
            when (commandName) {
                PingCommand.name -> PingCommand.execute(event)
                else -> {
                    event.reply("${Emojis.error} Unknown command.")
                        .setEphemeral(true)
                        .queue()
                }
            }
        } catch (e: Exception) {
            println("\u001B[31m[CommandHandler] Error in /$commandName: ${e.message}\u001B[0m")
            if (!event.isAcknowledged) {
                event.reply("${Emojis.error} Error executing command!")
                    .setEphemeral(true)
                    .queue()
            }
        }
    }
}
