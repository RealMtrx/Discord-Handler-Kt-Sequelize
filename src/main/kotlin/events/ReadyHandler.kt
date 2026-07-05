package events

import net.dv8tion.jda.api.events.session.ReadyEvent

object ReadyHandler {
    suspend fun execute(event: ReadyEvent, bot: Bot) {
        val self = event.jda.selfUser
        println("\u001B[32m[Ready] Logged in as ${self.name} (${self.id})\u001B[0m")
    }
}
