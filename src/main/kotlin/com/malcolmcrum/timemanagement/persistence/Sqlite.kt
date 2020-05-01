package com.malcolmcrum.timemanagement.persistence

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.Connection

fun createDatabase(url: String = "jdbc:sqlite:timemanagement.db") {
    Database.connect(url, driver = "org.sqlite.JDBC")
    TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE // see https://github.com/JetBrains/Exposed/wiki/FAQ#q-sqlite3-fails-with-the-error-transaction-attempt-0-failed-sqlite-supports-only-transaction_serializable-and-transaction_read_uncommitted

    transaction {
        SchemaUtils.create (Passwords, Users, Timesheets)
    }
}