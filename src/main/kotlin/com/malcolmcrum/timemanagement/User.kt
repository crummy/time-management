package com.malcolmcrum.timemanagement

data class User(val id: UserId, val name: String, val permission: Permission) {
    enum class Permission {
        USER,
        MANAGER,
        ADMIN
    }
}

data class NewUser(val id: UserId, val name: String, val password: Password) {
    fun toUser() = User(id, name, User.Permission.USER)
}

inline class UserId(val id: String)

data class UserLogin(val id: UserId, val password: Password)

inline class PasswordHash(val hash: ByteArray)

inline class Password(val password: String)