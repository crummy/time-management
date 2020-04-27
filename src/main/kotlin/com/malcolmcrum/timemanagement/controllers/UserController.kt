package com.malcolmcrum.timemanagement.controllers

import com.malcolmcrum.timemanagement.security.Permissions.authorizeManageUsers
import com.malcolmcrum.timemanagement.User
import com.malcolmcrum.timemanagement.badRequest
import com.malcolmcrum.timemanagement.notFound
import com.malcolmcrum.timemanagement.persistence.UserDao
import io.ktor.application.ApplicationCall
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import io.ktor.sessions.set

class UserController(private val userDao: UserDao) {
    companion object {
        val emptyResponse = mapOf<String, String>()
    }

    suspend fun delete(call: ApplicationCall) {
        authorizeManageUsers(call)
        val userId = call.parameters["userId"]!!
        val removed = userDao.remove(userId)
        call.respond(removed ?: emptyResponse)
    }

    suspend fun getAll(call: ApplicationCall) {
        authorizeManageUsers(call)
        call.respond(userDao.getAll())
    }

    suspend fun getOne(call: ApplicationCall) {
        authorizeManageUsers(call)
        val userId = call.parameters["userId"]!!
        val user = userDao[userId] ?: return call.notFound("No user found: $userId")
        call.respond(user)
    }

    suspend fun update(call: ApplicationCall) {
        authorizeManageUsers(call)
        val userId = call.parameters["userId"]!!
        val user = call.receive(User::class)
        if (user.id != userId) {
            return call.badRequest("User ID doesn't match: ${user.id} and $userId")
        }
        userDao[userId] = user
        val currentUser = call.sessions.get<User>()
        if (currentUser?.id == user.id) {
            call.sessions.set(user)
        }
        call.respond(user)
    }
}