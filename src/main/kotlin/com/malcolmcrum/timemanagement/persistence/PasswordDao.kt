package com.malcolmcrum.timemanagement.persistence

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class PasswordDao {
    operator fun get(userId: String): String? {
        return transaction {
            val query = Passwords.select { Passwords.userId eq userId }
            if (query.count() > 0) {
                query.first()[Passwords.password]
            } else {
                null
            }
        }
    }

    operator fun set(userId: String, password: String) {
        transaction {
            Passwords.insert {
                it[Passwords.userId] = userId
                it[Passwords.password] = password
            }
        }
    }
}

internal object Passwords: IntIdTable() {
    val userId = varchar("userId", 256)
    val password = varchar("hashedPassword", 256)
}