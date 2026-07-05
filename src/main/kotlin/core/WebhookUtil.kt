package core

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URI

object WebhookUtil {
    suspend fun sendWebhook(url: String, content: String, botName: String) {
        withContext(Dispatchers.IO) {
            try {
                val json = """{"content":"${escapeJson(content)}"}"""
                val uri = URI(url).toURL()
                val conn = uri.openConnection() as java.net.HttpURLConnection
                conn.requestMethod = "POST"
                conn.doOutput = true
                conn.setRequestProperty("Content-Type", "application/json")
                conn.outputStream.use { it.write(json.toByteArray()) }
                conn.responseCode
                conn.disconnect()
            } catch (_: Exception) {
            }
        }
    }

    private fun escapeJson(s: String): String {
        return s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r")
    }
}
