package com.malcolmcrum.timemanagement

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.application.ApplicationCall
import io.ktor.http.HttpStatusCode.Companion.Forbidden
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.request.receive
import io.ktor.response.respond


class SecurityController(private val passwordDao: PasswordDao,
                         private val passwordHasher: PasswordHasher,
                         private val simpleJwt: SimpleJWT) {
    suspend fun login(call: ApplicationCall) {
        val login = call.receive(UserLogin::class)
        val hash = passwordHasher.toHash(login.password)
        val existingHash = passwordDao[login.id]
        if (hash == existingHash) {
            val jwt = simpleJwt.sign(login.id)
            call.respond(OK, jwt)
        } else {
            call.respond(Forbidden, "User ${login.id} denied login")
        }
    }

}

class SimpleJWT(secret: String) {
    private val algorithm = Algorithm.HMAC256(secret)
    val verifier = JWT.require(algorithm).build()!!

    fun sign(userId: String): String = JWT.create().withClaim(USER_ID, userId).sign(algorithm)

    companion object {
        const val USER_ID = "userId"
    }
}