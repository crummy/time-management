package com.malcolmcrum.timemanagement

import io.ktor.application.ApplicationCall
import io.ktor.sessions.get
import io.ktor.sessions.sessions

class ForbiddenException(val user: String?) : RuntimeException("User $user denied access")

object Permissions {
    fun authorizeManageUsers(call: ApplicationCall) {
        val user = call.sessions.get<User>()
        when (user?.permission) {
            User.Permission.ADMIN -> return
            User.Permission.MANAGER -> return
            else -> throw ForbiddenException(user?.id)
        }
    }

    fun authorizeManageTimesheet(call: ApplicationCall, userId: String) {
        val user = call.sessions.get<User>()
        when {
            user?.id == userId -> return
            user?.permission == User.Permission.ADMIN -> return
            user?.permission == User.Permission.MANAGER -> return
            else -> throw ForbiddenException(user?.id)
        }
    }
}