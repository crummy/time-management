package com.malcolmcrum.timemanagement.helpers

import com.malcolmcrum.timemanagement.User
import com.malcolmcrum.timemanagement.persistence.PasswordDao
import com.malcolmcrum.timemanagement.persistence.TimesheetDao
import com.malcolmcrum.timemanagement.persistence.UserDao
import com.malcolmcrum.timemanagement.security.PasswordHasher
import com.malcolmcrum.timemanagement.startServer
import io.ktor.application.Application

fun testEnvironment(vararg users: User): Application.() -> Unit {
    val passwordDao = PasswordDao()
    val timesheetDao = TimesheetDao()
    val userDao = UserDao()
    users.forEach {user ->
        userDao[user.id] = user
        // For the test, passwords are the same as their userIds.
        passwordDao[user.id] = PasswordHasher.toHash(user.id)
    }
    return { startServer(passwordDao, timesheetDao, userDao) }
}