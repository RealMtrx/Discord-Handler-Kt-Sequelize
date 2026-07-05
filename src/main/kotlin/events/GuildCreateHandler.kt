package events

import net.dv8tion.jda.api.events.guild.GuildJoinEvent

object GuildCreateHandler {
    suspend fun execute(event: GuildJoinEvent, bot: Bot) {
        val guild = event.guild
        println("\u001B[36m[GuildCreate] Joined: ${guild.name} (${guild.id})\u001B[0m")
    }
}
