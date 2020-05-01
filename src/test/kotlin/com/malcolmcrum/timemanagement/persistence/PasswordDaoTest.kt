package com.malcolmcrum.timemanagement.persistence

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.test.assertEquals
import kotlin.test.assertNull

internal class PasswordDaoTest {
    lateinit var passwordDao: PasswordDao

    @BeforeEach
    fun `set up database`() {
        val url = "jdbc:sqlite:test.db"
        Files.deleteIfExists(Paths.get("test.db"))
        createDatabase(url)
        passwordDao = PasswordDao()
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