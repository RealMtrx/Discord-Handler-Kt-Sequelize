package core

data class CooldownResult(val onCooldown: Boolean, val timeLeft: Long = 0)

object CommandUtils {
    private val cooldowns = mutableMapOf<String, Long>()

    fun checkCooldown(userId: String, commandName: String, cooldownMs: Long = 3000): CooldownResult {
        val key = "$userId:$commandName"
        val now = System.currentTimeMillis()
        val lastUsed = cooldowns[key] ?: 0
        val remaining = cooldownMs - (now - lastUsed)

        return if (remaining > 0) {
            CooldownResult(true, remaining / 1000)
        } else {
            cooldowns[key] = now
            CooldownResult(false)
        }
    }
}
