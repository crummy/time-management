package com.malcolmcrum.timemanagement.persistence

import com.malcolmcrum.timemanagement.NewTimesheet
import com.malcolmcrum.timemanagement.Timesheet
import java.util.concurrent.atomic.AtomicInteger

class TimesheetDao {
    var ids = AtomicInteger(1000)
    val timesheets: MutableMap<Int, Timesheet> = mutableMapOf()

    fun forUser(userId: String) = timesheets.values.filter { it.userId == userId }.sortedBy { it.date }
    fun getAll() = timesheets.values.sortedBy { it.date }

    operator fun set(id: Int, value: Timesheet) {
        timesheets[id] = value
    }

    fun delete(id: Int) = timesheets.remove(id)
    operator fun get(id: Int) = timesheets[id]
    fun save(userId: String, timesheet: NewTimesheet) {
        val id = ids.getAndIncrement()
        timesheets[id] = Timesheet(id, userId, timesheet.description, timesheet.date, timesheet.hours)
    }
}
