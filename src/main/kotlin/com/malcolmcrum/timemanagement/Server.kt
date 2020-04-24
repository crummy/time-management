package com.malcolmcrum.timemanagement
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CORS
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.content.default
import io.ktor.http.content.files
import io.ktor.http.content.static
import io.ktor.routing.*
import io.ktor.serialization.json
import io.ktor.sessions.SessionTransportTransformerMessageAuthentication
import io.ktor.sessions.Sessions
import io.ktor.sessions.cookie
import org.slf4j.event.Level

fun Application.main() {
    val userDao = UserDao()
    val passwordDao = PasswordDao()
    val passwordHasher = PasswordHasher()
    val userController = UserController(userDao, passwordDao, passwordHasher)
    val securityController = SecurityController(passwordDao, passwordHasher, userDao)

    install(DefaultHeaders)
    install(CallLogging) {
        level = Level.INFO
    }
    install(ContentNegotiation) {
        json()
    }
    install(CORS) { // TODO remove this when hosted
        anyHost()
        method(HttpMethod.Options)
        header("Sec-Fetch-Dest")
        header("User-Agent")
        header("Referer")
        header("Content-Type")
        header("Accept")
    }
    install(Sessions) {
        cookie<String>(USER_SESSION) {
            cookie.path = "/"
            val secretSignKey = "averylongkeythatishardtoguess".toByteArray()
            transform(SessionTransportTransformerMessageAuthentication(secretSignKey))
        }
    }
    routing {
        route("api") {
            get("check") { securityController.check(call) }
            post("login") { securityController.login(call) }
            route("users") {
                get { userController.getAll(call) }
                post { userController.create(call) }
                route(":userId") {
                    get { userController.getOne(call) }
                    patch { userController.update(call) }
                    delete { userController.delete(call) }
                }
            }
        }
        static("") {
            files("ui/public")
            default("ui/public/index.html")
        }
    }
}