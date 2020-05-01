package com.malcolmcrum.timemanagement.persistence

import com.malcolmcrum.timemanagement.NewTimesheet
import com.malcolmcrum.timemanagement.Timesheet
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Paths
import java.time.LocalDate
import kotlin.test.assertEquals

internal class TimesheetDaoTest {
    lateinit var timesheetDao: TimesheetDao

    @BeforeEach
    fun `set up database`() {
        val url = "jdbc:sqlite:test.db"
        Files.deleteIfExists(Paths.get("test.db"))
        createDatabase(url)
        timesheetDao = TimesheetDao()
    }

    @Test
    fun `add and read new timesheet`() {
        val newTimesheet = NewTimesheet("worked hard", LocalDate.now(), 16f)
        val id = timesheetDao.save("username", newTimesheet)
        val timesheet = timesheetDao[id]
        assertEquals(Timesheet(id, "username", newTimesheet.description, newTimesheet.date, newTimesheet.hours), timesheet)
    }
}