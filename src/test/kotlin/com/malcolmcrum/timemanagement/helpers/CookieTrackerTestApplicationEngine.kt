package com.malcolmcrum.timemanagement.helpers

import io.ktor.http.Cookie
import io.ktor.http.HttpMethod
import io.ktor.http.encodeURLParameter
import io.ktor.http.parseServerSetCookieHeader
import io.ktor.server.testing.TestApplicationCall
import io.ktor.server.testing.TestApplicationEngine
import io.ktor.server.testing.TestApplicationRequest
import io.ktor.server.testing.handleRequest

fun TestApplicationEngine.cookiesSession(
        initialCookies: List<Cookie> = listOf(),
        callback: CookieTrackerTestApplicationEngine.() -> Unit
) {
    callback(CookieTrackerTestApplicationEngine(this, initialCookies))
}

class CookieTrackerTestApplicationEngine(
        val engine: TestApplicationEngine,
        var trackedCookies: List<Cookie> = listOf()
)

fun CookieTrackerTestApplicationEngine.handleRequest(
        method: HttpMethod,
        uri: String,
        setup: TestApplicationRequest.() -> Unit = {}
): TestApplicationCall {
    return engine.handleRequest(method, uri) {
        val cookieValue = trackedCookies.map { (it.name).encodeURLParameter() + "=" + (it.value).encodeURLParameter() }.joinToString("; ")
        addHeader("Cookie", cookieValue)
        setup()
    }.apply {
        trackedCookies = response.headers.values("Set-Cookie").map { parseServerSetCookieHeader(it) }
    }
}