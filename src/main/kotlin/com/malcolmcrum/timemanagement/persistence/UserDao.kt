package com.malcolmcrum.timemanagement.persistence

import com.malcolmcrum.timemanagement.User
import com.malcolmcrum.timemanagement.persistence.Users.name
import com.malcolmcrum.timemanagement.persistence.Users.permission
import com.malcolmcrum.timemanagement.persistence.Users.preferredHours
import com.malcolmcrum.timemanagement.persistence.Users.userId
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class UserDao {
    operator fun get(id: String): User? {
        return transaction {
            Users.select { userId eq id }
                    .map { it.toUser() }
                    .firstOrNull()
        }
    }

    fun add(user: User) = transaction {
        Users.insert {
            it[userId] = user.id
            it[name] = user.name
            it[permission] = user.permission
            it[preferredHours] = user.preferredWorkingHoursPerDay
        }
    }

    operator fun set(id: String, user: User) {
        return transaction {
            Users.update({ userId eq id }) {
                it[name] = user.name
                it[permission] = user.permission
                it[preferredHours] = user.preferredWorkingHoursPerDay
            }
        }
    }

    fun getAll(): List<User> {
        return transaction {
            Users.selectAll().map { it.toUser() }
        }
    }

    fun remove(userId: String) {
        return transaction {
            Users.deleteWhere { Users.userId eq userId }
        }
    }

    private fun ResultRow.toUser() = User(this[userId], this[name], this[permission], this[preferredHours])
}

internal object Users : IntIdTable() {
    val userId = varchar("userId", 256).uniqueIndex()
    val name = varchar("name", 256)
    val permission = enumerationByName("permission", 16, User.Permission::class)
    val preferredHours = float("preferredHours")
}