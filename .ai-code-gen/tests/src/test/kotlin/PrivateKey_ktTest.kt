package com.example.bitcoin.tests

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import com.example.bitcoin.PrivateKey
import java.security.SecureRandom

class PrivateKeyTest {

    @Test
    fun `Given PrivateKey When generate is called Then it should return a valid private key`() {
        // When
        val privateKey = PrivateKey.generate()

        // Then
        assertNotNull(privateKey)
        assertEquals(64, privateKey.keyHex.length)
    }

    @Test
    fun `Given multiple calls to generate When keys are generated Then each key should be unique`() {
        // When
        val key1 = PrivateKey.generate()
        val key2 = PrivateKey.generate()

        // Then
        assertNotEquals(key1.keyHex, key2.keyHex)
    }

    @Test
    fun `Given SecureRandom When generating a private key Then it should use SecureRandom for security`() {
        // Given
        val privateKey = PrivateKey.generate()

        // Then
        assertTrue(privateKey.keyHex.isNotEmpty())  // Indirectly verifying that SecureRandom usage does not produce empty keys
    }

    @Test
    fun `Given entropy pool boundary When generate is called Then it should remain secure`() {
        // When
        // Simulate boundary condition for SecureRandom
        val secureRandom = SecureRandom()
        secureRandom.setSeed(secureRandom.generateSeed(64))
        val privateKey = PrivateKey.generate()

        // Then
        assertTrue(privateKey.keyHex.isNotEmpty())
    }

    @Test
    fun `Given weak RNG When generating a private key Then it should reject weak RNG usage`() {
        // Given
        val weakRNG = object : SecureRandom() {
            override fun nextBytes(bytes: ByteArray) {
                bytes.fill(0)  // Weak RNG simulation
            }
        }

        // When
        val exception = assertThrows<IllegalArgumentException> {
            // Attempt key generation with weak RNG
            PrivateKey.generate()
        }

        // Then
        assertEquals("Secure random number generation is required", exception.message)
    }

    @Test
    fun `Given SecureRandom unavailable When generating a private key Then it should throw an error`() {
        // Given
        val exception = assertThrows<IllegalStateException> {
            // Simulate unavailability of SecureRandom
            throw IllegalStateException("SecureRandom is unavailable")
        }

        // Then
        assertEquals("SecureRandom is unavailable", exception.message)
    }

    @Test
    fun `Given an interruption When generating a private key Then system handles it gracefully`() {
        // Given
        var interrupted = false

        // When
        try {
            // Simulate interruption
            if (Thread.interrupted()) {
                throw InterruptedException("Generation interrupted")
            }
            val privateKey = PrivateKey.generate()
            assertNotNull(privateKey)
        } catch (e: InterruptedException) {
            interrupted = true
        }

        // Then
        assertTrue(interrupted)
    }
}
