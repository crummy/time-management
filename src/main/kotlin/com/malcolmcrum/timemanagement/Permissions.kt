package com.malcolmcrum.timemanagement

import io.ktor.application.ApplicationCall
import io.ktor.sessions.get
import io.ktor.sessions.sessions

object Permissions {
    fun authorizeManageUsers(call: ApplicationCall) {
        val user = call.sessions.get<User>()
        when (user?.permission) {
            User.Permission.ADMIN -> return
            User.Permission.MANAGER -> return
            else -> throw RuntimeException()
        }
    }
}