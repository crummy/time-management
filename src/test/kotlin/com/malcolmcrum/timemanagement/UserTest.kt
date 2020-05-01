package com.malcolmcrum.timemanagement

import com.malcolmcrum.timemanagement.helpers.cookiesSession
import com.malcolmcrum.timemanagement.helpers.handleRequest
import com.malcolmcrum.timemanagement.helpers.testEnvironment
import com.malcolmcrum.timemanagement.helpers.toJson
import com.malcolmcrum.timemanagement.persistence.Passwords
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.server.testing.setBody
import io.ktor.server.testing.withTestApplication
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class UserTest {

    private val user = User("userId", "Test User", User.Permission.USER, 8f)
    private val manager = User("managerId", "Test Manager", User.Permission.MANAGER, 4f)
    private val admin = User("adminId", "Test Admin", User.Permission.ADMIN, 2f)

    @AfterEach
    fun `clear database`() {
        transaction {
            SchemaUtils.drop(Passwords)
        }
    }

    @Test
    fun `test admin can list users`() = withTestApplication(testEnvironment(user, manager, admin)) {
        cookiesSession {
            handleRequest(HttpMethod.Post, "/api/login") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(UserLogin(admin.id, admin.id).toJson())
            }

            handleRequest(HttpMethod.Get, "/api/users").apply {
                assertEquals(200, response.status()?.value)
                assertEquals(listOf(user, manager, admin).toJson(), response.content)
            }
        }
    }

    @Test
    fun `test user deletion`() = withTestApplication(testEnvironment(user, manager, admin)) {
        cookiesSession {
            handleRequest(HttpMethod.Post, "/api/login") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(UserLogin(manager.id, manager.id).toJson())
            }

            handleRequest(HttpMethod.Delete, "/api/users/${user.id}").apply {
                assertEquals(200, response.status()?.value)
            }

            handleRequest(HttpMethod.Get, "/api/users").apply {
                assertEquals(listOf(manager, admin).toJson(), response.content)
            }
        }
    }

    @Test
    fun `test user updating`() = withTestApplication(testEnvironment(user, manager, admin)) {
        val updatedUser = User(user.id, user.name, user.permission, 0f)
        cookiesSession {
            handleRequest(HttpMethod.Post, "/api/login") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(UserLogin(manager.id, manager.id).toJson())
            }

            handleRequest(HttpMethod.Patch, "/api/users/${user.id}") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(updatedUser.toJson())
            }.apply {
                assertEquals(200, response.status()?.value)
            }

            handleRequest(HttpMethod.Get, "/api/users/${user.id}").apply {
                assertEquals(updatedUser.toJson(), response.content)
            }
        }
    }

    @Test
    fun `test user cannot make themselves admin`() = withTestApplication(testEnvironment(user, manager, admin)) {
        val updatedUser = User(user.id, user.name, User.Permission.ADMIN, user.preferredWorkingHoursPerDay)
        cookiesSession {
            handleRequest(HttpMethod.Post, "/api/login") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(UserLogin(manager.id, manager.id).toJson())
            }

            handleRequest(HttpMethod.Patch, "/api/users/${user.id}") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(updatedUser.toJson())
            }.apply {
                assertEquals(403, response.status()?.value)
            }
        }
    }
}