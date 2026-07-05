package commands.prefix.public

import core.CommandUtils
import core.Emojis
import net.dv8tion.jda.api.entities.Message

object PingCommand {
    val name = "ping"
    val description = "Replies with Pong!"
    val aliases = listOf("p")
    val cooldownMs = 3000L

    suspend fun execute(message: Message, args: List<String>) {
        val cooldown = CommandUtils.checkCooldown(
            message.author?.id ?: "unknown", name, cooldownMs
        )
        if (cooldown.onCooldown) {
            message.reply("${Emojis.loading} Please wait ${cooldown.timeLeft} seconds before using this command again.").queue()
            return
        }

        message.reply("${Emojis.success} Pong!").queue()
    }
}
