package com.malcolmcrum.timemanagement

data class User(val id: UserId, val name: String, val permission: Permission) {
    enum class Permission {
        USER,
        MANAGER,
        ADMIN
    }
}

inline class UserId(val id: String)
