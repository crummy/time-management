package com.malcolmcrum.timemanagement

import java.time.LocalDate

data class Timesheet(val id: String, val userId: String, val description: String, val date: LocalDate, val hours: Float)