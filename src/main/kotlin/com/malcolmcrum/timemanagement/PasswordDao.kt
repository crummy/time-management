package com.malcolmcrum.timemanagement

class PasswordDao {
    private val passwords = mutableMapOf<UserId, PasswordHash>()

    operator fun get(id: UserId): PasswordHash? {
        return passwords[id]
    }

    operator fun set(id: UserId, password: PasswordHash) {
        passwords[id] = password
    }
}