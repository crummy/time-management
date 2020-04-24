package com.malcolmcrum.timemanagement

class UserDao {
    private val users = mutableMapOf<String, User>()

    operator fun get(id: String): User? {
        return users[id]
    }

    operator fun set(id: String, user: User) {
        users[id] = user
    }

    fun getAll(): Collection<User> {
        return users.values
    }

    fun remove(userId: String): User? {
        return users.remove(userId)
    }
}