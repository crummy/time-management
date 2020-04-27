package com.malcolmcrum.timemanagement

class TimesheetDao {
    val timesheets: MutableMap<String, Timesheet> = mutableMapOf()

    fun forUser(userId: String) = timesheets.values.filter { it.userId == userId }.sortedBy { it.date }
    fun getAll() = timesheets.values.sortedBy { it.date }

    operator fun set(id: String, value: Timesheet) {
        timesheets[id] = value
    }

    fun delete(id: String) = timesheets.remove(id)
}
