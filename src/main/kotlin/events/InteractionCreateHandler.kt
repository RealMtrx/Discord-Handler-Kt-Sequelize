package events

import handlers.CommandHandler
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent

object InteractionCreateHandler {
    suspend fun execute(event: SlashCommandInteractionEvent, bot: Bot) {
        CommandHandler.execute(event)
    }
}
