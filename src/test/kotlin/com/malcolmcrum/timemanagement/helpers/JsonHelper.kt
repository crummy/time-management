package com.malcolmcrum.timemanagement.helpers

import com.malcolmcrum.timemanagement.User
import com.malcolmcrum.timemanagement.UserLogin
import kotlinx.serialization.builtins.list
import kotlinx.serialization.json.Json

fun UserLogin.toJson(): String {
    return Json.Default.stringify(UserLogin.serializer(), this)
}

fun User.toJson(): String {
    return Json.Default.stringify(User.serializer(), this)
}

fun List<User>.toJson(): String {
    return Json.Default.stringify(User.serializer().list, this)
}