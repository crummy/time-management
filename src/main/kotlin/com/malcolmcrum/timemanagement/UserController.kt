package com.malcolmcrum.timemanagement

import com.malcolmcrum.timemanagement.Permissions.authorizeManageUsers
import io.ktor.application.ApplicationCall
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.sessions.sessions

class UserController(private val userDao: UserDao,
                     private val passwordDao: PasswordDao,
                     private val passwordHasher: PasswordHasher) {

    companion object {
        val emptyResponse = mapOf<String, String>()
    }

    suspend fun create(call: ApplicationCall) {
        val newUser = call.receive(NewUser::class)
        val hash = passwordHasher.toHash(newUser.password)
        val user = newUser.toUser()
        userDao[user.id] = user
        passwordDao[user.id] = hash
        call.sessions.set(USER_SESSION, user.id)
        call.respond(user)
    }

    suspend fun delete (call: ApplicationCall) {
        authorizeManageUsers(call)
        val userId = call.parameters["userId"]!!
        val removed = userDao.remove(userId)
        call.respond(removed ?: emptyResponse)
    }

    suspend fun getAll(call: ApplicationCall) {
        authorizeManageUsers(call)
        call.respond(userDao.getAll())
    }

    suspend fun getOne (call: ApplicationCall) {
        authorizeManageUsers(call)
        val userId = call.parameters["userId"]!!
        val user = userDao[userId] ?: return call.notFound("No user found: $userId")
        call.respond(user)
    }

    suspend fun update (call: ApplicationCall) {
        authorizeManageUsers(call)
        val userId = call.parameters["userId"]!!
        val user = call.receive(User::class)
        if (user.id != userId) {
            return call.badRequest("User ID doesn't match: ${user.id} and $userId")
        }
        userDao[userId] = user
        call.respond(user)
    }
}