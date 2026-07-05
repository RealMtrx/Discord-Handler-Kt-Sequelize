package commands.slash.public

import core.CommandUtils
import core.Emojis
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent

object PingCommand {
    val name = "ping"
    val description = "Replies with Pong!"
    val cooldownMs = 3000L

    suspend fun execute(event: SlashCommandInteractionEvent) {
        val cooldown = CommandUtils.checkCooldown(
            event.user.id, name, cooldownMs
        )
        if (cooldown.onCooldown) {
            event.reply("${Emojis.loading} Please wait ${cooldown.timeLeft} seconds before using this command again.")
                .setEphemeral(true)
                .queue()
            return
        }

        event.reply("${Emojis.success} Pong!")
            .setEphemeral(true)
            .queue()
    }
}
