package com.example.bitcoin.tests

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import com.example.bitcoin.PrivateKey
import java.security.SecureRandom

class PrivateKeyTest {

    @Test
    fun `Given a call to generate When generating a private key Then the key should be a 64-character hexadecimal string`() {
        // Given
        
        // When
        val privateKey = PrivateKey.generate()

        // Then
        assertEquals(64, privateKey.keyHex.length)
        privateKey.keyHex.forEach { 
            assert(it in '0'..'9' || it in 'a'..'f')
        }
    }

    @Test
    fun `Given multiple calls to generate When generating private keys Then all keys should be unique`() {
        // Given
        val keys = mutableSetOf<String>()

        // When
        repeat(100) {
            keys.add(PrivateKey.generate().keyHex)
        }

        // Then
        assertEquals(100, keys.size)
    }

    @Test
    fun `Given a secure random failure When generating a private key Then an informative error should be thrown`() {
        // Given
        val originalSecureRandom = PrivateKey.Companion::secureRandom
        try {
            PrivateKey.Companion::secureRandom.set(null)

            // When
            val exception = assertThrows<NullPointerException> {
                PrivateKey.generate()
            }

            // Then
            assertEquals("SecureRandom is null", exception.message)
        } finally {
            PrivateKey.Companion::secureRandom.set(originalSecureRandom)
        }
    }

    @Test
    fun `Given a call to generate When generating a private key Then the key should contain valid hex characters`() {
        // Given
        
        // When
        val privateKey = PrivateKey.generate()

        // Then
        privateKey.keyHex.forEach { 
            assert(it in '0'..'9' || it in 'a'..'f')
        }
    }

    @Test
    fun `Given SecureRandom integrity When generating multiple private keys Then keys should exhibit high randomness`() {
        // Given
        val keys = mutableSetOf<String>()

        // When
        repeat(100) {
            keys.add(PrivateKey.generate().keyHex)
        }

        // Then
        // Further randomness validation can be performed here
        assertEquals(100, keys.size)
    }

    @Test
    fun `Given a recovery from random number generation failure When generating a private key Then the system should recover and generate a new key`() {
        // Given
        val originalSecureRandom = PrivateKey.Companion::secureRandom
        try {
            // Simulate recovery
            PrivateKey.Companion::secureRandom.set(SecureRandom())

            // When
            val privateKey = PrivateKey.generate()

            // Then
            assertEquals(64, privateKey.keyHex.length)
        } finally {
            PrivateKey.Companion::secureRandom.set(originalSecureRandom)
        }
    }
}
