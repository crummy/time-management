package com.malcolmcrum.timemanagement

import io.javalin.http.BadRequestResponse
import io.javalin.http.Context
import io.javalin.http.NotFoundResponse

class UserController(private val userDao: UserDao,
                     private val passwordDao: PasswordDao,
                     private val passwordHasher: PasswordHasher) {

    companion object {
        val emptyResponse = mapOf<String, String>()
    }

    fun create(ctx: Context) {
        val newUser = ctx.bodyAsClass(NewUser::class.java)
        val hash = passwordHasher.toHash(newUser.password)
        val user = newUser.toUser()
        userDao[user.id] = user
        passwordDao[user.id] = hash
        ctx.json(user)
    }

    fun delete (ctx: Context) {
        val id = ctx.pathParam("userId")
        val userId = UserId(id)
        val removed = userDao.remove(userId)
        ctx.json(removed ?: emptyResponse)
    }

    fun getAll(ctx: Context) {
        ctx.json(userDao.getAll())
    }

    fun getOne (ctx: Context) {
        val id = ctx.pathParam("userId")
        val userId = UserId(id)
        val user = userDao[userId] ?: throw NotFoundResponse("No user found: $userId")
        ctx.json(user)
    }

    fun update (ctx: Context) {
        val id = ctx.pathParam("userId")
        val userId = UserId(id)
        val user = ctx.bodyAsClass(User::class.java)
        if (user.id != userId) {
            throw BadRequestResponse("User ID doesn't match: ${user.id} and $userId")
        }
        userDao[userId] = user
        ctx.json(user)
    }
}