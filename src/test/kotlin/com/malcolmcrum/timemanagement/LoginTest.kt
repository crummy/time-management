package com.malcolmcrum.timemanagement

import com.malcolmcrum.timemanagement.helpers.cookiesSession
import com.malcolmcrum.timemanagement.helpers.handleRequest
import com.malcolmcrum.timemanagement.helpers.testEnvironment
import com.malcolmcrum.timemanagement.helpers.toJson
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.setBody
import io.ktor.server.testing.withTestApplication
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class LoginTest {

    private val user = User("userId", "Test User", User.Permission.USER, 8f)
    private val manager = User("managerId", "Test Manager", User.Permission.MANAGER, 4f)
    private val admin = User("adminId", "Test Admin", User.Permission.ADMIN, 2f)

    @Test
    fun `test login with user missing`() {
        withTestApplication(testEnvironment(user, manager, admin)) {
            with(handleRequest(HttpMethod.Post, "/api/login") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(UserLogin("missinguser", "password").toJson())
            }) {
                assertEquals(403, response.status()?.value)
            }
        }
    }

    @Test
    fun `test login with wrong password`() = withTestApplication(testEnvironment(user, manager, admin)) {
        with(handleRequest(HttpMethod.Post, "/api/login") {
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(UserLogin(user.id, "wrongpassword").toJson())
        }) {
            assertEquals(403, response.status()?.value)
        }
    }

    @Test
    fun `test successful login`() = withTestApplication(testEnvironment(user, manager, admin)) {
        cookiesSession {
            handleRequest(HttpMethod.Post, "/api/login") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(UserLogin(user.id, user.id).toJson())
            }.apply {
                assertEquals(200, response.status()?.value)
                assertEquals(user.toJson(), response.content)
            }

            handleRequest(HttpMethod.Get, "/api/check").apply {
                assertEquals(200, response.status()?.value)
            }
        }
    }

}
