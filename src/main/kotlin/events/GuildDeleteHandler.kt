package events

import net.dv8tion.jda.api.events.guild.GuildLeaveEvent

object GuildDeleteHandler {
    suspend fun execute(event: GuildLeaveEvent, bot: Bot) {
        val guild = event.guild
        println("\u001B[33m[GuildDelete] Left: ${guild.name} (${guild.id})\u001B[0m")
    }
}
