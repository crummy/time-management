package com.malcolmcrum.timemanagement

import io.javalin.http.Context
import io.javalin.http.ForbiddenResponse

class SecurityController(private val passwordDao: PasswordDao,
                         private val passwordHasher: PasswordHasher) {
    fun login(ctx: Context) {
        val login = ctx.bodyAsClass(UserLogin::class.java)
        val hash = passwordHasher.toHash(login.password)
        val existingHash = passwordDao[login.id]
        if (hash == existingHash) {
            ctx.login()
            ctx.status(200)
        } else {
            throw ForbiddenResponse("User ${login.id} denied login")
        }
    }

    @JvmName("addLoginHeader")
    private fun Context.login() {
        this.cookieStore("login", "foo")
    }

}