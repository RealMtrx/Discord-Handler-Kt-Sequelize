import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    try {
        val config = Config.load()
        val bot = Bot(config)
        bot.start()
    } catch (e: Exception) {
        println("\u001B[31m[Fatal] Failed to start bot: ${e.message}\u001B[0m")
        e.printStackTrace()
    }
}
