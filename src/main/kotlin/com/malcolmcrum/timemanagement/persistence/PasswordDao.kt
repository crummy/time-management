package com.malcolmcrum.timemanagement.persistence

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class PasswordDao {
    operator fun get(userId: String) = transaction {
        Passwords.select { Passwords.userId eq userId }
                .map { it[Passwords.password] }
                .firstOrNull()
    }

    operator fun set(userId: String, password: String) = transaction {
        Passwords.insert {
            it[Passwords.userId] = userId
            it[Passwords.password] = password
        }
    }
}

internal object Passwords : IntIdTable() {
    val userId = varchar("userId", 256).uniqueIndex().references(Users.userId)
    val password = varchar("hashedPassword", 256)
}