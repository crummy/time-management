package com.malcolmcrum.timemanagement
import com.malcolmcrum.timemanagement.controllers.SecurityController
import com.malcolmcrum.timemanagement.controllers.TimesheetController
import com.malcolmcrum.timemanagement.controllers.USER_SESSION
import com.malcolmcrum.timemanagement.controllers.UserController
import com.malcolmcrum.timemanagement.persistence.PasswordDao
import com.malcolmcrum.timemanagement.persistence.TimesheetDao
import com.malcolmcrum.timemanagement.persistence.UserDao
import com.malcolmcrum.timemanagement.security.ForbiddenException
import com.malcolmcrum.timemanagement.security.PasswordHasher
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.*
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.*
import io.ktor.serialization.json
import io.ktor.sessions.SessionStorageMemory
import io.ktor.sessions.Sessions
import io.ktor.sessions.cookie
import org.slf4j.event.Level
import work.jeong.murry.ktor.features.EasySpaFeature

fun Application.main() {
    val userDao = UserDao()
    val passwordDao = PasswordDao()
    val passwordHasher = PasswordHasher()
    val userController = UserController(userDao)
    val securityController = SecurityController(passwordDao, passwordHasher, userDao)
    val timesheetDao = TimesheetDao()
    val timesheetController = TimesheetController(timesheetDao)

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
        cookie<User>(USER_SESSION, SessionStorageMemory()) {
            cookie.path = "/"
        }
    }
    install(EasySpaFeature) {
        staticRootDocs = "ui/public"
        defaultFile = "index.html"
        apiUrl = "/api"
    }
    install(StatusPages) {
        exception<ForbiddenException> {
            call.respond(HttpStatusCode.Forbidden)
        }
    }
    routing {
        route("api") {
            get("check") { securityController.check(call) }
            post("login") { securityController.login(call) }
            post("signup") { securityController.signUp(call)}
            get("signout") { securityController.signOut(call)}
            route("users") {
                get { userController.getAll(call) }
                route("{userId}") {
                    get { userController.getOne(call) }
                    patch { userController.update(call) }
                    delete { userController.delete(call) }
                    route("timesheets") {
                        get { timesheetController.get(call) }
                        post { timesheetController.add(call)}
                        route("{timesheetId}") {
                            patch { timesheetController.update(call) }
                            delete { timesheetController.delete(call) }
                        }
                    }
                }
            }
            route("timesheets") {
                get { timesheetController.getAll(call) }
            }
        }
    }
}