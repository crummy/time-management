package com.malcolmcrum.timemanagement.helpers

import com.malcolmcrum.timemanagement.NewTimesheet
import com.malcolmcrum.timemanagement.Timesheet
import com.malcolmcrum.timemanagement.User
import com.malcolmcrum.timemanagement.UserLogin
import kotlinx.serialization.builtins.list
import kotlinx.serialization.json.Json

fun UserLogin.toJson() = Json.Default.stringify(UserLogin.serializer(), this)

fun User.toJson() = Json.Default.stringify(User.serializer(), this)

fun List<User>.toJson() = Json.Default.stringify(User.serializer().list, this)

fun NewTimesheet.toJson() = Json.Default.stringify(NewTimesheet.serializer(), this)

@JvmName("toTimesheetJson")
fun List<Timesheet>.toJson() = Json.Default.stringify(Timesheet.serializer().list, this)
