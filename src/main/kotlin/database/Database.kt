package database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import models.Users
import org.jetbrains.exposed.sql.Database as ExposedDatabase
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object Database {
    private var dataSource: HikariDataSource? = null

    fun connect(config: Config): Boolean {
        return try {
            val hikariConfig = HikariConfig().apply {
                jdbcUrl = config.dbUrl
                driverClassName = config.dbDriver
                username = config.dbUser
                password = config.dbPassword
                maximumPoolSize = 10
                minimumIdle = 2
                idleTimeout = 30000
                maxLifetime = 600000
            }
            dataSource = HikariDataSource(hikariConfig)
            ExposedDatabase.connect(dataSource!!)

            transaction {
                SchemaUtils.create(Users)
            }

            println("\u001B[32m[Database] Connected (Dialect: ${config.dbDialect})\u001B[0m")
            true
        } catch (e: Exception) {
            println("\u001B[31m[Database] Connection failed: ${e.message}\u001B[0m")
            false
        }
    }

    fun disconnect() {
        dataSource?.close()
    }
}
