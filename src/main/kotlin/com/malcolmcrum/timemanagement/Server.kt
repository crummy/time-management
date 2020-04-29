package com.malcolmcrum.timemanagement
import com.malcolmcrum.timemanagement.controllers.*
import com.malcolmcrum.timemanagement.persistence.PasswordDao
import com.malcolmcrum.timemanagement.persistence.TimesheetDao
import com.malcolmcrum.timemanagement.persistence.UserDao
import com.malcolmcrum.timemanagement.security.ForbiddenException
import com.malcolmcrum.timemanagement.security.PasswordHasher
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.features.StatusPages
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.*
import io.ktor.serialization.json
import io.ktor.sessions.SessionStorageMemory
import io.ktor.sessions.Sessions
import io.ktor.sessions.cookie
import kotlinx.serialization.modules.serializersModuleOf
import org.slf4j.event.Level
import work.jeong.murry.ktor.features.EasySpaFeature
import java.time.LocalDate

@Suppress("unused")
fun Application.startServer() {
    val userDao = UserDao()
    val passwordDao = PasswordDao()
    val timesheetDao = TimesheetDao()
    startServer(passwordDao, timesheetDao, userDao)
}

fun Application.startServer(passwordDao: PasswordDao, timesheetDao: TimesheetDao, userDao: UserDao) {
    val userController = UserController(userDao)
    val securityController = SecurityController(passwordDao, PasswordHasher, userDao)
    val timesheetController = TimesheetController(timesheetDao)
    val summaryController = SummaryController(timesheetDao)

    install(DefaultHeaders)
    install(CallLogging) {
        level = Level.INFO
    }
    install(ContentNegotiation) {
        json(module = serializersModuleOf(LocalDate::class, LocalDateSerializer))
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
            call.respond(HttpStatusCode.Forbidden, mapOf("error" to it.message))
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
                        route("summary") {
                            get { summaryController.get(call) }
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