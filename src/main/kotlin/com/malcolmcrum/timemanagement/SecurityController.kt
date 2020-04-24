package com.malcolmcrum.timemanagement

import io.ktor.application.ApplicationCall
import io.ktor.http.HttpStatusCode.Companion.Forbidden
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.sessions.sessions


const val USER_SESSION = "user"

class SecurityController(private val passwordDao: PasswordDao,
                         private val passwordHasher: PasswordHasher,
private val userDao: UserDao) {
    suspend fun login(call: ApplicationCall) {
        val login = call.receive(UserLogin::class)
        val hash = passwordHasher.toHash(login.password)
        val existingHash = passwordDao[login.id]
        if (hash == existingHash) {
            val user = userDao[login.id]!!
            call.sessions.set(USER_SESSION, user.id)
            call.respond(OK)
        } else {
            call.respond(Forbidden, "User ${login.id} denied login")
        }
    }

    suspend fun check(call: ApplicationCall) {
        val user = call.sessions.get(USER_SESSION)
        //val test = call.sessions.get<User>()
        when (user) {
            null -> call.respond(Forbidden)
            else -> call.respond(OK)
        }
    }
}