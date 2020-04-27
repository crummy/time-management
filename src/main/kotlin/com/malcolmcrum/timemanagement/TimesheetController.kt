package com.malcolmcrum.timemanagement

import io.ktor.application.ApplicationCall
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.request.receive
import io.ktor.response.respond

class TimesheetController(val timesheetDao: TimesheetDao) {
    suspend fun getAll(call: ApplicationCall) {
        val selected = timesheetDao.getAll()
        call.respond(selected)
    }

    suspend fun get(call: ApplicationCall) {
        val userId = call.parameters["userId"]!!
        Permissions.authorizeManageTimesheet(call, userId)
        val selected = timesheetDao.forUser(userId)
        call.respond(selected)
    }

    suspend fun update(call: ApplicationCall) {
        val userId = call.parameters["userId"]!!
        Permissions.authorizeManageTimesheet(call, userId)
        val timesheet = call.receive(Timesheet::class)
        Permissions.authorizeManageTimesheet(call, timesheet.userId)
        timesheetDao[timesheet.id] = timesheet
        call.respond(timesheet)
    }

    suspend fun delete(call: ApplicationCall) {
        val userId = call.parameters["userId"]!!
        Permissions.authorizeManageTimesheet(call, userId)
        val timesheet = call.receive(Timesheet::class)
        Permissions.authorizeManageTimesheet(call, timesheet.userId)
        timesheetDao.delete(timesheet.id)
        call.respond(OK)
    }

}