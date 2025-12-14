package com.example.bitcoin.tests

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import com.example.bitcoin.PublicKey

class PublicKeyValidationTest {

    @Test
    fun `Given a valid public key string When creating PublicKey Then object is created successfully`() {
        // Given
        val validPublicKeyString = "1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa"

        // When
        val publicKey = PublicKey(value = validPublicKeyString)

        // Then
        assertEquals(validPublicKeyString, publicKey.value)
    }

    @Test
    fun `Given an invalid format public key string When creating PublicKey Then error is thrown`() {
        // Given
        val invalidPublicKeyString = "InvalidKey123"

        // When & Then
        val exception = assertThrows(IllegalArgumentException::class.java) {
            PublicKey(value = invalidPublicKeyString)
        }
        assertEquals("Invalid public key format.", exception.message)
    }

    @Test
    fun `Given a null public key string When creating PublicKey Then error is thrown`() {
        // Given
        val nullPublicKeyString: String? = null

        // When & Then
        val exception = assertThrows(IllegalArgumentException::class.java) {
            PublicKey(value = nullPublicKeyString)
        }
        assertEquals("Public key cannot be null.", exception.message)
    }

    @Test
    fun `Given a public key with special characters When creating PublicKey Then error is thrown`() {
        // Given
        val specialCharacterPublicKeyString = "@#$%^&*()"

        // When & Then
        val exception = assertThrows(IllegalArgumentException::class.java) {
            PublicKey(value = specialCharacterPublicKeyString)
        }
        assertEquals("Public key contains invalid characters.", exception.message)
    }

    @Test
    fun `Given a blank public key string When creating PublicKey Then error is thrown`() {
        // Given
        val blankPublicKeyString = ""

        // When & Then
        val exception = assertThrows(IllegalArgumentException::class.java) {
            PublicKey(value = blankPublicKeyString)
        }
        assertEquals("Public key cannot be blank.", exception.message)
    }

    @Test
    fun `Given a minimum length valid public key string When creating PublicKey Then object is created successfully`() {
        // Given
        val minLengthPublicKeyString = "1A1zP"

        // When
        val publicKey = PublicKey(value = minLengthPublicKeyString)

        // Then
        assertEquals(minLengthPublicKeyString, publicKey.value)
    }

    @Test
    fun `Given a maximum length valid public key string When creating PublicKey Then object is created successfully`() {
        // Given
        val maxLengthPublicKeyString = "1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa"

        // When
        val publicKey = PublicKey(value = maxLengthPublicKeyString)

        // Then
        assertEquals(maxLengthPublicKeyString, publicKey.value)
    }

    @Test
    fun `Given a public key string with potential injection When creating PublicKey Then error is thrown`() {
        // Given
        val injectionPublicKeyString = "1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa; DROP TABLE users;"

        // When & Then
        val exception = assertThrows(IllegalArgumentException::class.java) {
            PublicKey(value = injectionPublicKeyString)
        }
        assertEquals("Invalid public key format.", exception.message)
    }
}