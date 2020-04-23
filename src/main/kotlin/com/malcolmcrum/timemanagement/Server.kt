package com.malcolmcrum.timemanagement

import io.javalin.Javalin;
import io.javalin.apibuilder.ApiBuilder.*;
import org.slf4j.Logger
import org.slf4j.LoggerFactory

val log: Logger = LoggerFactory.getLogger("SERVER")

fun main() {
    val port = System.getenv()["PORT"]?.toInt() ?: 8080

    val app = Javalin.create { config ->
        config.defaultContentType = "application/json"
        //config.addStaticFiles("/public")
        config.enableCorsForAllOrigins()
        config.requestLogger { ctx, ms -> log.info("${ctx.method()} ${ctx.path()} ${ctx.status()} - ${ms}ms") }
    }

    val userDao = UserDao()
    val passwordDao = PasswordDao()
    val passwordHasher = PasswordHasher()
    val userController = UserController(userDao, passwordDao, passwordHasher)
    val securityController = SecurityController(passwordDao, passwordHasher)

    app.routes {
        post("login", securityController::login)
        path("users") {
            get(userController::getAll)
            post(userController::create)
            path(":user-id") {
                get(userController::getOne)
                patch(userController::update)
                delete(userController::delete)
            }
        }
    }

    app.start(port)
}

data class Error(val error: String)