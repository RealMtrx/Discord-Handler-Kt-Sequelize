package handlers

import events.*
import net.dv8tion.jda.api.events.guild.GuildJoinEvent
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.events.session.ReadyEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import kotlinx.coroutines.runBlocking

class EventHandler(private val bot: Bot) : ListenerAdapter() {
    override fun onReady(event: ReadyEvent) {
        runBlocking { ReadyHandler.execute(event, bot) }
    }

    override fun onMessageReceived(event: MessageReceivedEvent) {
        runBlocking { MessageCreateHandler.execute(event, bot) }
    }

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        runBlocking { InteractionCreateHandler.execute(event, bot) }
    }

    override fun onGuildJoin(event: GuildJoinEvent) {
        runBlocking { GuildCreateHandler.execute(event, bot) }
    }

    override fun onGuildLeave(event: GuildLeaveEvent) {
        runBlocking { GuildDeleteHandler.execute(event, bot) }
    }
}
