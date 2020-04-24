package com.malcolmcrum.timemanagement

import io.ktor.application.ApplicationCall
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import kotlinx.serialization.Serializable

@Serializable
data class Error(val error: String)

suspend fun ApplicationCall.notFound(message: String) {
    this.respond(HttpStatusCode.NotFound, Error(message))
}

suspend fun ApplicationCall.badRequest(message: String) {
    this.respond(HttpStatusCode.BadRequest, Error(message))
}