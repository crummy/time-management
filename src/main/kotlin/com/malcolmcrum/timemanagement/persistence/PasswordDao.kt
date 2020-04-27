package com.malcolmcrum.timemanagement.persistence

class PasswordDao {
    private val passwords = mutableMapOf<String, String>()

    operator fun get(id: String): String? {
        return passwords[id]
    }

    operator fun set(id: String, password: String) {
        passwords[id] = password
    }
}