package com.malcolmcrum.timemanagement

import com.malcolmcrum.timemanagement.helpers.cookiesSession
import com.malcolmcrum.timemanagement.helpers.handleRequest
import com.malcolmcrum.timemanagement.helpers.testEnvironment
import com.malcolmcrum.timemanagement.helpers.toJson
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.server.testing.setBody
import io.ktor.server.testing.withTestApplication
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDate

internal class TimesheetTest {

    private val user = User("userId", "Test User", User.Permission.USER, 8f)

    @Test
    fun `test list empty timesheets`() = withTestApplication(testEnvironment(user)) {
        cookiesSession {
            handleRequest(HttpMethod.Post, "/api/login") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(UserLogin(user.id, user.id).toJson())
            }

            handleRequest(HttpMethod.Get, "/api/users/${user.id}/timesheets").apply {
                assertEquals(200, response.status()?.value)
                assertEquals("[]", response.content)
            }
        }
    }

    @Test
    fun `test add timesheet`() = withTestApplication(testEnvironment(user)) {
        val newTimesheet = NewTimesheet("did work", LocalDate.now(), 8f)
        val timesheet = Timesheet(1000, user.id, newTimesheet.description, newTimesheet.date, newTimesheet.hours)
        cookiesSession {
            handleRequest(HttpMethod.Post, "/api/login") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(UserLogin(user.id, user.id).toJson())
            }

            handleRequest(HttpMethod.Post, "/api/users/${user.id}/timesheets") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(newTimesheet.toJson())
            }

            handleRequest(HttpMethod.Get, "/api/users/${user.id}/timesheets").apply {
                assertEquals(200, response.status()?.value)
                assertEquals(listOf(timesheet).toJson(), response.content)
            }
        }
    }

}
