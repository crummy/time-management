package com.malcolmcrum.timemanagement.security

import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec


object PasswordHasher {
    fun toHash(password: String): String {
        val spec = PBEKeySpec(password.toCharArray(), SALT.toByteArray(), 65536, 128)
        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")

        val hashed = factory.generateSecret(spec).encoded
        return java.util.Base64.getEncoder().encodeToString(hashed)
    }

    private val SALT = System.getenv("PASSWORD_SALT") ?: "timemanagementbymalcolmcrum"
}