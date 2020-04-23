package com.malcolmcrum.timemanagement

class UserDao {
    private val users = mutableMapOf<UserId, User>()

    operator fun get(id: UserId): User? {
        return users[id]
    }

    operator fun set(id: UserId, user: User) {
        users[id] = user
    }

    fun getAll(): Collection<User> {
        return users.values
    }

    fun remove(userId: UserId): User? {
        return users.remove(userId)
    }
}