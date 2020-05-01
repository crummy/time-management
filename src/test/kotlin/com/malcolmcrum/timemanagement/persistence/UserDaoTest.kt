package com.malcolmcrum.timemanagement.persistence

import com.malcolmcrum.timemanagement.User
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.test.assertEquals

internal class UserDaoTest {
    lateinit var userDao: UserDao

    @BeforeEach
    fun `set up database`() {
        val url = "jdbc:sqlite:test.db"
        Files.deleteIfExists(Paths.get("test.db"))
        createDatabase(url)
        userDao = UserDao()
    }

    @Test
    fun `add and read user`() {
        val user = User("username", "michael render", User.Permission.USER, 8f)
        userDao.add(user)
        val readUser = userDao[user.id]
        assertEquals(user, readUser)
    }

    @Test
    fun `update and get user`() {
        val user = User("username", "michael render", User.Permission.USER, 8f)
        userDao.add(user)
        val updatedUser = user.copy(name = "jaime meline")
        userDao[user.id] = updatedUser
        val readUser = userDao[user.id]
        assertEquals(updatedUser, readUser)
    }
}