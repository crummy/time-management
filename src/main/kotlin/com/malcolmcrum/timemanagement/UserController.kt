package com.malcolmcrum.timemanagement

import io.javalin.http.BadRequestResponse
import io.javalin.http.Context
import io.javalin.http.NotFoundResponse

class UserController {
    val users = mutableMapOf<UserId, User>()

    companion object {
        val emptyResponse = mapOf<String, String>()
    }

    fun create(ctx: Context) {
        val user = ctx.bodyAsClass(User::class.java)
        users[user.id] = user
        ctx.json(user)
    }

    fun delete (ctx: Context) {
        val id = ctx.pathParam("userId")
        val userId = UserId(id)
        val removed = users.remove(userId)
        ctx.json(removed ?: emptyResponse)
    }

    fun getAll(ctx: Context) {
        ctx.json(users.values)
    }

    fun getOne (ctx: Context) {
        val id = ctx.pathParam("userId")
        val userId = UserId(id)
        val user = users[userId] ?: throw NotFoundResponse("No user found: $userId")
        ctx.json(user)
    }

    fun update (ctx: Context) {
        val id = ctx.pathParam("userId")
        val userId = UserId(id)
        val user = ctx.bodyAsClass(User::class.java)
        if (user.id != userId) {
            throw BadRequestResponse("User ID doesn't match: ${user.id} and $userId")
        }
        users[userId] = user
        ctx.json(user)
    }
}