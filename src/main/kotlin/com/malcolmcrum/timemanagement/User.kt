package com.malcolmcrum.timemanagement

import kotlinx.serialization.Serializable

@Serializable
data class User(val id: String, val name: String, val permission: Permission) {
    enum class Permission {
        USER,
        MANAGER,
        ADMIN
    }
}

@Serializable
data class NewUser(val id: String, val name: String, val password: String) {
    fun toUser() = User(id, name, User.Permission.ADMIN)
}

@Serializable
data class UserLogin(val id: String, val password: String)