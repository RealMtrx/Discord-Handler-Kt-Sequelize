# Discord Handler Kotlin (Sequelize)

A modern, feature-rich Discord bot handler built with **JDA v5** and **Exposed ORM**, featuring both slash commands and prefix commands with a robust modular architecture designed for scalability and maintainability.

## 🚀 Features

- **Dual Command System**: Support for both slash commands and prefix commands
- **Modular Architecture**: Clean separation of concerns with dedicated handlers
- **Anti-Crash System**: Comprehensive error handling and monitoring
- **Coroutine-Based**: Fully asynchronous with Kotlin coroutines
- **Webhook Logging**: Real-time logging for errors and guild events
- **Exposed ORM Integration**: SQL-based persistent data storage
- **Cooldown System**: Per-command cooldown management
- **Environment Configuration**: Secure configuration with dotenv-kotlin

## 📁 Project Structure

```
Discord-Handler-Kt-Sequelize/
├── build.gradle.kts                # Gradle build configuration
├── settings.gradle.kts             # Gradle settings
├── src/main/kotlin/
│   ├── Main.kt                     # Main bot entry point
│   ├── Config.kt                   # Bot configuration from .env
│   ├── Bot.kt                      # Bot initialization
│   ├── core/                       # Core utilities
│   │   ├── CommandUtils.kt         # Cooldown and utilities
│   │   ├── Emojis.kt               # Centralized emoji definitions
│   │   └── WebhookUtil.kt          # Webhook utility
│   ├── database/
│   │   └── Database.kt             # Exposed ORM connection setup
│   ├── events/                     # Discord event handlers
│   │   ├── ReadyHandler.kt         # Bot ready event
│   │   ├── MessageCreateHandler.kt # Handles prefix commands
│   │   ├── InteractionCreateHandler.kt # Handles slash command interactions
│   │   ├── GuildCreateHandler.kt   # Handler when bot joins a server
│   │   └── GuildDeleteHandler.kt   # Handler when bot leaves a server
│   ├── handlers/                   # Handlers for modularity
│   │   ├── AntiCrash.kt            # Crash prevention and error handling
│   │   ├── CommandHandler.kt       # Slash command routing
│   │   ├── EventHandler.kt         # Event listener dispatcher
│   │   ├── PrefixHandler.kt        # Prefix command routing
│   │   └── Logger.kt               # Logger for bot activity
│   ├── models/
│   │   └── User.kt                 # User data model (Exposed Table)
│   └── commands/
│       ├── slash/public/
│       │   └── PingCommand.kt      # Example slash ping command
│       └── prefix/public/
│           └── PingCommand.kt      # Example prefix ping command
```

## 🔧 Installation

1. **Clone the repository**

   ```bash
   git clone https://github.com/RealMtrx/Discord-Handler-Kt-Sequelize.git
   cd Discord-Handler-Kt-Sequelize
   ```

2. **Generate Gradle wrapper and build**

   ```bash
   gradle wrapper
   ./gradlew build
   ```

3. **Environment Setup**

   Copy `.env.example` to `.env` and fill in your values:

   ```env
   TOKEN=your_bot_token_here
   PREFIX=$
   DB_URL=jdbc:postgresql://localhost:5432/discord
   ERROR_WEBHOOK=https://discord.com/api/webhooks/your_webhook
   ```

4. **Run the bot**

   ```bash
   ./gradlew run
   ```

## 📋 Dependencies

- **JDA**: v5.2.1 - Java Discord API wrapper
- **Exposed ORM**: v0.57.0 - SQL ORM framework
- **HikariCP**: v5.1.0 - Connection pooling
- **dotenv-kotlin**: v6.4.1 - Environment variable management
- **PostgreSQL**: v42.7.4 - Database driver

## 📝 Command Development

### Creating Slash Commands

Create a new file in `src/Commands/Slash/Public/[name].kt`:

```kotlin
package commands.slash.public

import core.CommandUtils
import core.Emojis
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent

object PingCommand {
    val name = "ping"
    val description = "Replies with Pong!"
    val cooldownMs = 3000L

    suspend fun execute(event: SlashCommandInteractionEvent) {
        event.reply("${Emojis.success} Pong!").setEphemeral(true).queue()
    }
}
```

### Creating Prefix Commands

Create a new file in `src/Commands/Prefix/Public/[name].kt`:

```kotlin
package commands.prefix.public

import core.Emojis
import net.dv8tion.jda.api.entities.Message

object PingCommand {
    val name = "ping"

    suspend fun execute(message: Message, args: List<String>) {
        message.reply("${Emojis.success} Pong!").queue()
    }
}
```

---

**Discord Handler** — Built by **Mtrx** — Discord: **0hu2**
