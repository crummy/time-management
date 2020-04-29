package com.malcolmcrum.timemanagement.security

import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec


object PasswordHasher {
    fun toHash(password: String): String {
        val spec = PBEKeySpec(password.toCharArray(), SALT.toByteArray(), 65536, 128)
        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")

        return String(factory.generateSecret(spec).encoded)
    }

    val SALT = System.getenv("PASSWORD_SALT") ?: "timemanagementbymalcolmcrum"
}