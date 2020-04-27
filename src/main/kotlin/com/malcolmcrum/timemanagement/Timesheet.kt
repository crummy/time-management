package com.malcolmcrum.timemanagement

import kotlinx.serialization.*
import java.time.LocalDate

@Serializable
data class Timesheet(val id: Int, val userId: String, val description: String, @Serializable(with = LocalDateSerializer::class) val date: LocalDate, val hours: Float)

@Serializable
data class NewTimesheet(val description: String, @Serializable(with = LocalDateSerializer::class) val date: LocalDate, val hours: Float)

@Serializer(forClass = LocalDate::class)
object LocalDateSerializer : KSerializer<LocalDate> {
    override fun serialize(encoder: Encoder, date: LocalDate) {
        encoder.encodeString(date.toString())
    }

    override fun deserialize(decoder: Decoder): LocalDate {
        val date = decoder.decodeString()
        return LocalDate.parse(date)
    }
}