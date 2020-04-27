package com.malcolmcrum.timemanagement

import kotlinx.serialization.*
import java.time.LocalDate

@Serializable
data class Timesheet(val id: Int, val userId: String, val description: String, @Serializable(with = LocalDateSerializer::class) val date: LocalDate, val hours: Float)

@Serializable
data class NewTimesheet(val description: String, @Serializable(with = LocalDateSerializer::class) val date: LocalDate, val hours: Float)

@Serializer(forClass = LocalDate::class)
object LocalDateSerializer : KSerializer<LocalDate> {
    override fun serialize(encoder: Encoder, value: LocalDate) {
        encoder.encode(Date.serializer(), Date(value.dayOfMonth, value.monthValue, value.year))
    }

    override fun deserialize(decoder: Decoder): LocalDate {
        val date = decoder.decode(Date.serializer())
        return LocalDate.of(date.year, date.month, date.day)
    }
}

@Serializable
data class Date(val day: Int, val month: Int, val year: Int)