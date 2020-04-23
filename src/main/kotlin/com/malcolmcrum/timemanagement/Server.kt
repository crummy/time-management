package com.malcolmcrum.timemanagement

import io.javalin.Javalin;
import io.javalin.apibuilder.ApiBuilder.*;

fun main() {
    val port = System.getenv()["PORT"]?.toInt() ?: 8080

    val app = Javalin.create { config ->
        config.defaultContentType = "application/json"
        //config.addStaticFiles("/public")
        config.enableCorsForAllOrigins()
    }

    val userController = UserController()

    app.routes {
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