package com.malcolmcrum.timemanagement.controllers

import com.malcolmcrum.timemanagement.User
import com.malcolmcrum.timemanagement.badRequest
import com.malcolmcrum.timemanagement.notFound
import com.malcolmcrum.timemanagement.persistence.UserDao
import com.malcolmcrum.timemanagement.security.ForbiddenException
import com.malcolmcrum.timemanagement.security.Permissions.authorizeManageUsers
import io.ktor.application.ApplicationCall
import io.ktor.http.HttpStatusCode.Companion.OK
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
        userDao.remove(userId)
        call.respond(OK)
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
        val updatedUser = call.receive(User::class)
        if (updatedUser.id != userId) {
            return call.badRequest("User ID doesn't match: ${updatedUser.id} and $userId")
        }
        val userRequestingUpdate = call.sessions.get<User>()!!
        if (updatedUser.permission > userRequestingUpdate.permission) {
            throw ForbiddenException("User ${userRequestingUpdate.id} cannot set user $userId to ${updatedUser.permission}")
        }
        userDao[userId] = updatedUser
        val currentUser = call.sessions.get<User>()
        if (currentUser?.id == updatedUser.id) {
            call.sessions.set(updatedUser)
        }
        call.respond(updatedUser)
    }
}