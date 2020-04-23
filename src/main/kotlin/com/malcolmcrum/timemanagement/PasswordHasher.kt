package com.malcolmcrum.timemanagement

import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec


class PasswordHasher {
    fun toHash(password: Password): PasswordHash {
        val spec = PBEKeySpec(password.password.toCharArray(), SALT.toByteArray(), 65536, 128)
        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")

        return PasswordHash(factory.generateSecret(spec).encoded)
    }

    companion object {
        const val SALT = "timemanagementbymalcolmcrum"
    }
}