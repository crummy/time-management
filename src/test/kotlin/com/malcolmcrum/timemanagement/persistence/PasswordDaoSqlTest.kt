package com.malcolmcrum.timemanagement.persistence

import org.jetbrains.exposed.sql.SchemaUtils.drop
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

internal class PasswordDaoSqlTest {
    lateinit var passwordDao: PasswordDao

    @BeforeEach
    fun `set up database`() {
        val url = "jdbc:sqlite:test.db"
        createDatabase(url)
        passwordDao = PasswordDao()
    }

    @AfterEach
    fun `clear database`() {
        transaction {
            drop(Passwords)
        }
    }

    @Test
    fun `get missing password`() {
        val hash = passwordDao["missing"]
        assertNull(hash)
    }

    @Test
    fun `insert and retrieve password`() {
        passwordDao["userId"] = "password"
        val password = passwordDao["userId"]
        assertEquals("password", password)
    }
}