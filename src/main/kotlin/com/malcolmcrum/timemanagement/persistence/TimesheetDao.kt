package com.malcolmcrum.timemanagement.persistence

import com.malcolmcrum.timemanagement.NewTimesheet
import com.malcolmcrum.timemanagement.Timesheet
import com.malcolmcrum.timemanagement.persistence.Timesheets.date
import com.malcolmcrum.timemanagement.persistence.Timesheets.description
import com.malcolmcrum.timemanagement.persistence.Timesheets.hours
import com.malcolmcrum.timemanagement.persistence.Timesheets.id
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.`java-time`.date
import org.jetbrains.exposed.sql.transactions.transaction

class TimesheetDao {
    fun forUser(userId: String) = transaction {
        Timesheets.select { Timesheets.userId eq userId }
                .map { it.toTimesheet() }
                .sortedBy { it.date }
    }

    fun getAll() = transaction {
        Timesheets.selectAll()
                .map { it.toTimesheet() }
                .sortedBy { it.date }
    }

    operator fun set(id: Int, value: Timesheet) = transaction {
        Timesheets.update({ Timesheets.id eq id }) {
            it[userId] = value.userId
            it[description] = value.description
            it[date] = value.date
            it[hours] = value.hours
        }
    }

    fun delete(id: Int) = transaction {
        Timesheets.deleteWhere { Timesheets.id eq id }
    }

    operator fun get(id: Int) = transaction {
        Timesheets.select { Timesheets.id eq id }
                .map { it.toTimesheet() }
                .firstOrNull()
    }

    fun save(userId: String, timesheet: NewTimesheet) = transaction {
        Timesheets.insert {
            it[Timesheets.userId] = userId
            it[description] = timesheet.description
            it[date] = timesheet.date
            it[hours] = timesheet.hours
        } get Timesheets.id
    }

    private fun ResultRow.toTimesheet() = Timesheet(this[id], this[Timesheets.userId], this[description], this[date], this[hours])

}


internal object Timesheets : Table() {
    val id = integer("id").autoIncrement()
    override val primaryKey = PrimaryKey(id)
    val userId = varchar("userId", 256).references(Users.userId)
    val date = date("date")
    val description = varchar("description", 1024)
    val hours = float("hours")
}