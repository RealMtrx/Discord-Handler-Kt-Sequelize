package handlers

import core.WebhookUtil
import java.io.StringWriter
import java.io.PrintWriter

object AntiCrash {
    private var webhookUrl: String? = null
    private var botName: String = "Discord Handler"

    fun init(config: Config) {
        webhookUrl = config.errorWebhook
        botName = config.botName

        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            println("\u001B[31m[AntiCrash] Uncaught Exception in thread ${thread.name}: ${throwable.message}\u001B[0m")
            throwable.printStackTrace()
            sendError("Uncaught Exception", throwable)
        }

        println("\u001B[32m[AntiCrash] Active\u001B[0m")
    }

    private fun sendError(title: String, throwable: Throwable) {
        val url = webhookUrl ?: return
        val sw = StringWriter()
        throwable.printStackTrace(PrintWriter(sw))
        val stackTrace = sw.toString().take(1900)

        val description = "```\n$stackTrace\n```"
        val content = "$title\n\n$description"
        kotlinx.coroutines.runBlocking {
            WebhookUtil.sendWebhook(url, content, botName)
        }
    }

    fun reportError(title: String, error: String) {
        println("\u001B[31m[AntiCrash] $title: $error\u001B[0m")
        val url = webhookUrl ?: return
        kotlinx.coroutines.runBlocking {
            WebhookUtil.sendWebhook(url, "**$title**\n```\n$error\n```", botName)
        }
    }
}
