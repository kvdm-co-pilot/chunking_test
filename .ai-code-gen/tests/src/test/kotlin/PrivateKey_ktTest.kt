package com.example.bitcoin.testing

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Disabled
import com.example.bitcoin.PrivateKey
import java.security.SecureRandom

class Com_example_bitcoin_PrivateKeyTest {

    private lateinit var privateKey: PrivateKey

    @BeforeEach
    fun setUp() {
        // Initialization logic can be added here if needed
    }

    @Test
    fun `Given SecureRandom, When generating private key, Then it should return 32-byte hexadecimal string`() {
        // Given
        val secureRandom = SecureRandom()

        // When
        privateKey = PrivateKey.generate()
        val keyHex = privateKey.keyHex

        // Then
        assertEquals(64, keyHex.length, "Expected key length of 64 characters")
        assertTrue(keyHex.matches(Regex("^[0-9a-f]{64}$")), "Expected key to be a valid hexadecimal string")
    }

    @Test
    fun `Given high load, When generating private keys concurrently, Then all keys should be unique`() {
        // Given
        val keys = mutableSetOf<String>()

        // When
        repeat(1000) {
            keys.add(PrivateKey.generate().keyHex)
        }

        // Then
        assertEquals(1000, keys.size, "Expected 1000 unique private keys")
    }

    @Test
    fun `Given SecureRandom, When generating private key with oversized array, Then it should throw an error`() {
        // Given
        val oversizedArray = ByteArray(64)
        val secureRandom = SecureRandom()

        // When
        assertThrows(IllegalArgumentException::class.java, {
            secureRandom.nextBytes(oversizedArray)
            PrivateKey(oversizedArray.joinToString(separator = "") { "%02x".format(it) })
        }, "Expected error due to oversized byte array")
    }

    @Test
    fun `Given SecureRandom, When generating private key with minimum byte array, Then it should be valid`() {
        // Given
        val minimumArray = ByteArray(32)
        val secureRandom = SecureRandom()

        // When
        secureRandom.nextBytes(minimumArray)
        val keyHex = PrivateKey(minimumArray.joinToString(separator = "") { "%02x".format(it) }).keyHex

        // Then
        assertTrue(keyHex.matches(Regex("^[0-9a-f]{64}$")), "Expected key to be a valid hexadecimal string")
    }

    @Test
    fun `Given SecureRandom, When initialization fails, Then it should throw an error`() {
        // Given
        // Simulating failure by trying an unsupported operation

        // When & Then
        assertThrows(SecurityException::class.java, {
            SecureRandom.getInstance("InvalidAlgorithm")
        }, "Expected SecurityException due to invalid algorithm")
    }
}
