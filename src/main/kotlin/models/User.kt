package models

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.update
import org.jetbrains.exposed.sql.transactions.transaction

object Users : Table("users") {
    val userId = varchar("userId", 64)
    val points = integer("points").default(0)

    override val primaryKey = PrimaryKey(userId)
}

data class User(
    val userId: String,
    val points: Int = 0
) {
    companion object {
        fun findByUserId(userId: String): User? {
            return transaction {
                Users.select { Users.userId eq userId }
                    .singleOrNull()
                    ?.let { User(it[Users.userId], it[Users.points]) }
            }
        }

        fun save(user: User) {
            transaction {
                val existing = Users.select { Users.userId eq user.userId }.singleOrNull()
                if (existing != null) {
                    Users.update({ Users.userId eq user.userId }) {
                        it[points] = user.points
                    }
                } else {
                    Users.insert {
                        it[userId] = user.userId
                        it[points] = user.points
                    }
                }
            }
        }
    }
}
