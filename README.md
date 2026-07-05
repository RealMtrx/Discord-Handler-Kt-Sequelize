<div align="center">
  <h1>Discord Handler — Kotlin (SQL Edition)</h1>
  <p><strong>A production-ready Discord bot framework built with JDA v5 and Exposed ORM — supports SQLite, PostgreSQL, and MySQL with a modular src/ architecture.</strong></p>

  <p>
    <a href="https://github.com/RealMtrx/Discord-Handler-Kt-Sequelize/blob/main/LICENSE"><img src="https://img.shields.io/badge/license-MIT-blue.svg" alt="License"></a>
    <a href="https://github.com/RealMtrx/Discord-Handler-Kt-Sequelize/releases"><img src="https://img.shields.io/badge/version-0.9.0--beta-yellow" alt="Version 0.9.0 Beta"></a>
    <a href="https://github.com/RealMtrx/Discord-Handler-Kt-Sequelize/stargazers"><img src="https://img.shields.io/github/stars/RealMtrx/Discord-Handler-Kt-Sequelize" alt="Stars"></a>
    <a href="https://github.com/RealMtrx/Discord-Handler-Kt-Sequelize/issues"><img src="https://img.shields.io/github/issues/RealMtrx/Discord-Handler-Kt-Sequelize" alt="Issues"></a>
    <a href="https://github.com/RealMtrx/Discord-Handler-Kt-Sequelize/network"><img src="https://img.shields.io/github/forks/RealMtrx/Discord-Handler-Kt-Sequelize" alt="Forks"></a>
    <a href="https://github.com/RealMtrx/Discord-Handler/graphs/contributors"><img src="https://img.shields.io/badge/ecosystem-26%20repos-brightgreen" alt="26 Repos"></a>
    <a href="https://discord.gg/0hu2"><img src="https://img.shields.io/badge/discord-0hu2-5865F2" alt="Discord"></a>
  </p>

  <br>

  <p>
    <a href="#-features">Features</a> •
    <a href="#-quick-start">Quick Start</a> •
    <a href="#-project-structure">Structure</a> •
    <a href="#-database-configuration">Database Config</a> •
    <a href="#-api-reference">API</a> •
    <a href="#-mongodb-edition">MongoDB Edition</a> •
    <a href="#-related-repositories">Ecosystem</a>
  </p>
</div>

---

## Overview

Discord Handler Kotlin (SQL Edition) is the **Kotlin Sequelize variant** of the multi-language Discord Handler ecosystem. Built on **JDA v5** with **Exposed ORM**, it provides a coroutine-powered, event-driven foundation for Discord bots with dual command support (slash + prefix), relational database persistence, webhook-based logging, and a comprehensive anti-crash layer.

The entry point (`Main.kt`) boots in a predictable async sequence: initialize the anti-crash handler, connect the Exposed database, load slash commands, prefix commands, events, and Exposed table models, present a startup report, and finally log in. A graceful shutdown hook for `SIGINT`/`SIGTERM` is also registered via `Runtime.getRuntime().addShutdownHook`.

## Features

- **Dual Command System** — Slash commands via JDA's `SlashCommandInteractionEvent` and prefix commands via `MessageReceivedEvent`
- **Modular Architecture** — Separated concerns across `core/`, `database/`, `events/`, `handlers/`, `models/`, and `commands/`
- **Kotlin Coroutines** — Fully asynchronous with `suspend` functions and structured concurrency
- **Anti-Crash** — Global `Thread.setDefaultUncaughtExceptionHandler` interception via `AntiCrash.kt`
- **Webhook Logging** — Six dedicated webhooks: errors, slash commands, prefix commands, guild join, guild leave, and ready
- **Exposed ORM** — Type-safe SQL DSL via Exposed ORM with HikariCP connection pooling, supporting SQLite, PostgreSQL, and MySQL
- **Cooldown System** — Per-command cooldown tracked in `core/CommandUtils.kt`
- **Environment Configuration** — All secrets managed via `dotenv-kotlin` in `Config.kt`
- **Gradle Build** — Dependency management via Kotlin DSL with application plugin

## Quick Start

```bash
git clone https://github.com/RealMtrx/Discord-Handler-Kt-Sequelize.git
cd Discord-Handler-Kt-Sequelize
gradle wrapper
./gradlew build
```

Copy `.env.example` to `.env` and fill in your values:

```env
TOKEN=your_bot_token_here
CLIENT_ID=your_client_id_here
BOT_NAME=Discord Handler
PREFIX=$
OWNER_IDS=your_user_id_here

# Database Settings (Exposed ORM)
DB_DIALECT=POSTGRESQL
DB_URL=jdbc:postgresql://localhost:5432/discord
DB_DRIVER=org.postgresql.Driver
DB_USER=postgres
DB_PASSWORD=postgres

# Webhook URLs (optional)
ERROR_WEBHOOK=
SLASH_COMMAND_WEBHOOK=
PREFIX_COMMAND_WEBHOOK=
JOIN_GUILD_WEBHOOK=
LEAVE_GUILD_WEBHOOK=
READY_WEBHOOK=
```

```bash
./gradlew run
```

### Dependencies

| Package | Version | Purpose |
|---------|---------|---------|
| `JDA` | 5.2.1 | Discord API wrapper |
| `exposed-core` / `exposed-dao` / `exposed-jdbc` | 0.57.0 | Exposed ORM |
| `HikariCP` | 5.1.0 | Connection pooling |
| `dotenv-kotlin` | 6.4.1 | Environment variable management |
| `kotlinx-coroutines-core` | 1.9.0 | Coroutine support |
| `postgresql` | 42.7.4 | PostgreSQL JDBC driver |
| `logback-classic` | 1.5.13 | Logging framework |

## Project Structure

```
Discord-Handler-Kt-Sequelize/
├── build.gradle.kts                # Gradle build configuration
├── settings.gradle.kts             # Gradle settings
├── src/main/kotlin/
│   ├── Main.kt                     # Entry point — async boot sequence
│   ├── Config.kt                   # Bot configuration (token, prefixes, webhooks)
│   ├── Bot.kt                      # Bot initialization and client setup
│   ├── core/
│   │   ├── CommandUtils.kt         # Cooldown and utility helpers
│   │   ├── Emojis.kt               # Centralized emoji definitions
│   │   └── WebhookUtil.kt          # Webhook utility
│   ├── database/
│   │   └── Database.kt             # Exposed ORM connection setup
│   ├── events/
│   │   ├── ReadyHandler.kt         # Bot ready event
│   │   ├── MessageCreateHandler.kt # Handles prefix commands
│   │   ├── InteractionCreateHandler.kt # Handles slash command interactions
│   │   ├── GuildCreateHandler.kt   # Handler when bot joins a server
│   │   └── GuildDeleteHandler.kt   # Handler when bot leaves a server
│   ├── handlers/
│   │   ├── AntiCrash.kt            # Global error interception
│   │   ├── CommandHandler.kt       # Slash command routing
│   │   ├── EventHandler.kt         # Event listener dispatcher
│   │   ├── PrefixHandler.kt        # Prefix command routing
│   │   └── Logger.kt               # Logger for bot activity
│   ├── models/
│   │   └── User.kt                 # User data model (Exposed Table)
│   └── commands/
│       ├── slash/public/PingCommand.kt   # Example slash ping command
│       └── prefix/public/PingCommand.kt  # Example prefix ping command
```

## Database Configuration

Set `DB_DIALECT` to one of the following:

| Dialect | `DB_URL` Example | `DB_DRIVER` |
|---------|------------------|-------------|
| `SQLITE` | `jdbc:sqlite:discord_bot.db` | `org.sqlite.JDBC` |
| `POSTGRESQL` | `jdbc:postgresql://localhost:5432/discord` | `org.postgresql.Driver` |
| `MYSQL` | `jdbc:mysql://localhost:3306/discord` | `com.mysql.cj.jdbc.Driver` |

The `Database.kt` file dynamically configures the Exposed data source based on `DB_DIALECT`:

```kotlin
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database

object DatabaseFactory {
    fun init() {
        val dialect = Config.DB_DIALECT
        val config = HikariConfig().apply {
            driverClassName = Config.DB_DRIVER
            jdbcUrl = Config.DB_URL
            username = Config.DB_USER
            password = Config.DB_PASSWORD
            maximumPoolSize = 10
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        }
        val dataSource = HikariDataSource(config)
        Database.connect(dataSource)
    }
}
```

## API Reference

### Entry Point — `src/main/kotlin/Main.kt`

```kotlin
suspend fun main()
```

Creates a `JDABuilder` with `GatewayIntent.GUILD_MESSAGES` and `GatewayIntent.MESSAGE_CONTENT`. Loads handlers sequentially: AntiCrash → Database → slash commands → prefix commands → events → models. Logs in via `jda.build()`. Registers a `Runtime.getRuntime().addShutdownHook` for graceful shutdown. Uses `runBlocking` to bridge the coroutine entry point.

### Configuration — `src/main/kotlin/Config.kt`

| Key | Type | Description |
|-----|------|-------------|
| `TOKEN` | `String` | Discord bot token |
| `CLIENT_ID` | `String` | Discord application client ID |
| `BOT_NAME` | `String` | Display name for startup report |
| `PREFIX` | `String` | Prefix for text commands |
| `OWNER_IDS` | `String` | Comma-separated bot owner Discord IDs |
| `DB_DIALECT` | `String` | Database dialect (`SQLITE`, `POSTGRESQL`, `MYSQL`) |
| `DB_URL` | `String` | JDBC connection URL |
| `DB_DRIVER` | `String` | JDBC driver class |
| `DB_USER` | `String` | Database username |
| `DB_PASSWORD` | `String` | Database password |
| `ERROR_WEBHOOK` | `String` | Webhook URL for error reports |
| `SLASH_COMMAND_WEBHOOK` | `String` | Webhook URL for slash command usage |
| `PREFIX_COMMAND_WEBHOOK` | `String` | Webhook URL for prefix command usage |
| `JOIN_GUILD_WEBHOOK` | `String` | Webhook URL for guild joins |
| `LEAVE_GUILD_WEBHOOK` | `String` | Webhook URL for guild leaves |
| `READY_WEBHOOK` | `String` | Webhook URL for ready notifications |

### Events

| Event | File | Trigger |
|-------|------|---------|
| `Ready` | `events/ReadyHandler.kt` | Bot goes online — logs startup, sends ready webhook |
| `GuildJoin` | `events/GuildCreateHandler.kt` | Bot joins a server — sends join webhook |
| `GuildLeave` | `events/GuildDeleteHandler.kt` | Bot leaves a server — sends leave webhook |
| `SlashCommandInteraction` | `events/InteractionCreateHandler.kt` | Slash command used — routes to `CommandHandler` |
| `MessageReceived` | `events/MessageCreateHandler.kt` | Message sent — checks prefix, routes to `PrefixHandler` |

### Handlers

- **AntiCrash** — Registers `Thread.setDefaultUncaughtExceptionHandler` for global exception interception
- **CommandHandler** — Routes slash command interactions to the appropriate command module
- **PrefixHandler** — Routes prefix command messages to the appropriate command module
- **EventHandler** — Centralized event listener dispatcher
- **Logger** — Writes a startup report with command/event counts, database status, and anti-crash status

### Core Utilities

- **CommandUtils** — `checkCooldown` suspend helper and error formatting utilities
- **Emojis** — Centralized emoji constants for consistent bot responses
- **WebhookUtil** — `sendError`, `sendSlash`, `sendPrefix`, `sendJoin`, `sendLeave`, `sendReady` methods via Discord webhooks

## Adding Commands

### Slash Command

Create `src/main/kotlin/commands/slash/public/[Name].kt`:

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

### Prefix Command

Create `src/main/kotlin/commands/prefix/public/[Name].kt`:

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

The `CommandHandler` and `PrefixHandler` automatically discover and register new commands via reflection on the next restart. No manual wiring is needed.

## MongoDB Edition

A **MongoDB** variant of this handler is available for teams that prefer a document database:

[RealMtrx/Discord-Handler-Kt](https://github.com/RealMtrx/Discord-Handler-Kt)

It replaces `database/Database.kt` with a MongoDB driver connection (via `mongodb-driver-kotlin-coroutine`) and swaps Exposed Table definitions for BSON documents. All other modules — events, commands, handlers, core utilities — remain identical.

## Related Repositories

The Discord Handler ecosystem spans **26 repositories** across 13 languages, each available in both MongoDB and Sequelize editions.

### Base Repositories (MongoDB)

| Language | Repository |
|----------|------------|
| C++ | [RealMtrx/Discord-Handler-Cpp](https://github.com/RealMtrx/Discord-Handler-Cpp) |
| C# | [RealMtrx/Discord-Handler-Cs](https://github.com/RealMtrx/Discord-Handler-Cs) |
| Dart | [RealMtrx/Discord-Handler-Dart](https://github.com/RealMtrx/Discord-Handler-Dart) |
| Go | [RealMtrx/Discord-Handler-Go](https://github.com/RealMtrx/Discord-Handler-Go) |
| Java | [RealMtrx/Discord-Handler-Java](https://github.com/RealMtrx/Discord-Handler-Java) |
| JavaScript | [RealMtrx/Discord-Handler-Js](https://github.com/RealMtrx/Discord-Handler-Js) |
| Kotlin | [RealMtrx/Discord-Handler-Kt](https://github.com/RealMtrx/Discord-Handler-Kt) |
| Lua | [RealMtrx/Discord-Handler-Lua](https://github.com/RealMtrx/Discord-Handler-Lua) |
| PHP | [RealMtrx/Discord-Handler-Php](https://github.com/RealMtrx/Discord-Handler-Php) |
| Python | [RealMtrx/Discord-Handler-Py](https://github.com/RealMtrx/Discord-Handler-Py) |
| Ruby | [RealMtrx/Discord-Handler-Rb](https://github.com/RealMtrx/Discord-Handler-Rb) |
| Rust | [RealMtrx/Discord-Handler-Rs](https://github.com/RealMtrx/Discord-Handler-Rs) |
| TypeScript | [RealMtrx/Discord-Handler](https://github.com/RealMtrx/Discord-Handler) ← hub |

### Sequelize (SQL) Editions

| Language | Repository |
|----------|------------|
| C++ | [RealMtrx/Discord-Handler-Cpp-Sequelize](https://github.com/RealMtrx/Discord-Handler-Cpp-Sequelize) |
| C# | [RealMtrx/Discord-Handler-Cs-Sequelize](https://github.com/RealMtrx/Discord-Handler-Cs-Sequelize) |
| Dart | [RealMtrx/Discord-Handler-Dart-Sequelize](https://github.com/RealMtrx/Discord-Handler-Dart-Sequelize) |
| Go | [RealMtrx/Discord-Handler-Go-Sequelize](https://github.com/RealMtrx/Discord-Handler-Go-Sequelize) |
| Java | [RealMtrx/Discord-Handler-Java-Sequelize](https://github.com/RealMtrx/Discord-Handler-Java-Sequelize) |
| JavaScript | [RealMtrx/Discord-Handler-Js-Sequelize](https://github.com/RealMtrx/Discord-Handler-Js-Sequelize) |
| Kotlin | [RealMtrx/Discord-Handler-Kt-Sequelize](https://github.com/RealMtrx/Discord-Handler-Kt-Sequelize) |
| Lua | [RealMtrx/Discord-Handler-Lua-Sequelize](https://github.com/RealMtrx/Discord-Handler-Lua-Sequelize) |
| PHP | [RealMtrx/Discord-Handler-Php-Sequelize](https://github.com/RealMtrx/Discord-Handler-Php-Sequelize) |
| Python | [RealMtrx/Discord-Handler-Py-Sequelize](https://github.com/RealMtrx/Discord-Handler-Py-Sequelize) |
| Ruby | [RealMtrx/Discord-Handler-Rb-Sequelize](https://github.com/RealMtrx/Discord-Handler-Rb-Sequelize) |
| Rust | [RealMtrx/Discord-Handler-Rs-Sequelize](https://github.com/RealMtrx/Discord-Handler-Rs-Sequelize) |
| TypeScript | [RealMtrx/Discord-Handler-Ts-Sequelize](https://github.com/RealMtrx/Discord-Handler-Ts-Sequelize) |

> **[RealMtrx/Discord-Handler](https://github.com/RealMtrx/Discord-Handler)** — the TypeScript hub and flagship repository. Star it to support the ecosystem.

## License

Distributed under the MIT License. See `LICENSE` for more information.

---

Built by **Mtrx** — Discord: **0hu2** — [RealMtrx/Discord-Handler](https://github.com/RealMtrx/Discord-Handler)
