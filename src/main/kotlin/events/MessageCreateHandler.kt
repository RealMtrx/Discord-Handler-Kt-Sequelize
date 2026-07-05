package events

import handlers.PrefixHandler
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

object MessageCreateHandler {
    suspend fun execute(event: MessageReceivedEvent, bot: Bot) {
        val message = event.message
        if (message.author.idLong == bot.jda.selfUser.idLong) return
        PrefixHandler.execute(message, bot.config.prefix)
    }
}
