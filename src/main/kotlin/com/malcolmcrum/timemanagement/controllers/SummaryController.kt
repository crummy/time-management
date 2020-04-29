package com.malcolmcrum.timemanagement.controllers

import com.malcolmcrum.timemanagement.DaySummary
import com.malcolmcrum.timemanagement.Timesheet
import com.malcolmcrum.timemanagement.persistence.TimesheetDao
import com.malcolmcrum.timemanagement.security.Permissions
import io.ktor.application.ApplicationCall
import io.ktor.response.respond
import java.time.LocalDate

class SummaryController(private val timesheetDao: TimesheetDao) {
    suspend fun get(call: ApplicationCall) {
        val userId = call.parameters["userId"]!!
        Permissions.authorizeManageTimesheet(call, userId)
        val from = call.request.queryParameters["from"]?.let { LocalDate.parse(it) }
        val to = call.request.queryParameters["to"]?.let { LocalDate.parse(it) }
        val timesheets = timesheetDao.forUser(userId)
                .filter { from == null || it.date >= from }
                .filter { to == null || it.date <= to}
                .groupBy { it.date }
                .map { it.key.toString() to it.value.toDaySummary() } // Why toString? Can't get kotlinx.serialization to handle a Map<LocalDate, ?> - see https://stackoverflow.com/q/61492364/281657
                .toMap()
        call.respond(timesheets)
    }
}

private fun List<Timesheet>.toDaySummary(): DaySummary {
    val totalHours = sumByDouble { it.hours.toDouble() }.toFloat()
    val descriptions = map { it.description }
    return DaySummary(totalHours, descriptions)
}