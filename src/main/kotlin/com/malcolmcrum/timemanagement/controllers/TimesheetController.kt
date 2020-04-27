package com.malcolmcrum.timemanagement.controllers

import com.malcolmcrum.timemanagement.*
import com.malcolmcrum.timemanagement.persistence.TimesheetDao
import com.malcolmcrum.timemanagement.security.Permissions
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
        val timesheetId = call.parameters["timesheetId"]!!.toInt()
        val updatedTimesheet = call.receive(Timesheet::class)
        val timesheet = timesheetDao[timesheetId] ?: return call.notFound(timesheetId)
        if (timesheet.id != timesheetId) {
            return call.badRequest("Timesheet ID doesn't match: ${timesheet.id} and $timesheetId")
        } else if (timesheet.userId != userId) {
            return call.badRequest("User ID doesn't match: ${timesheet.userId} and $userId")
        }
        Permissions.authorizeManageTimesheet(call, timesheet.userId)
        timesheetDao[updatedTimesheet.id] = updatedTimesheet
        call.respond(updatedTimesheet)
    }

    suspend fun delete(call: ApplicationCall) {
        val userId = call.parameters["userId"]!!
        val timesheetId = call.parameters["timesheetId"]!!.toInt()
        Permissions.authorizeManageTimesheet(call, userId)
        val timesheet = timesheetDao[timesheetId] ?: return call.notFound(timesheetId)
        Permissions.authorizeManageTimesheet(call, timesheet.userId)
        timesheetDao.delete(timesheetId)
        call.respond(OK)
    }

    suspend fun add(call: ApplicationCall) {
        val userId = call.parameters["userId"]!!
        Permissions.authorizeManageTimesheet(call, userId)
        val newTimesheet = call.receive(NewTimesheet::class)
        val timesheet = timesheetDao.save(userId, newTimesheet)
        call.respond(timesheet)
    }

}