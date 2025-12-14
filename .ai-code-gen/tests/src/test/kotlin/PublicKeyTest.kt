package com.example.bitcoin.tests

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import com.example.bitcoin.PublicKey

class PublicKeyValidationTest {

    @Test
    fun `Given a valid Bitcoin public key When verified Then it should be accepted without errors`() {
        // Given
        val validPublicKey = PublicKey(value = "03a34f8a9c9e4b8f8d3e76bd9f4a3b5a2b3e6a5c8d7e9f3a4b8c7d6e5f4a3b2a1")

        // When
        val result = verifyPublicKey(validPublicKey)

        // Then
        assertEquals(true, result.isValid)
        assertEquals("", result.errorMessage)
    }

    @Test
    fun `Given an empty public key When verified Then it should be rejected with an error message`() {
        // Given
        val emptyPublicKey = PublicKey(value = "")

        // When
        val exception = assertThrows<IllegalArgumentException> {
            verifyPublicKey(emptyPublicKey)
        }

        // Then
        assertEquals("Public key cannot be empty.", exception.message)
    }

    @Test
    fun `Given a public key with incorrect format When verified Then it should throw a format error`() {
        // Given
        val incorrectFormatPublicKey = PublicKey(value = "12345abcde")

        // When
        val exception = assertThrows<IllegalArgumentException> {
            verifyPublicKey(incorrectFormatPublicKey)
        }

        // Then
        assertEquals("Public key format is incorrect.", exception.message)
    }

    @Test
    fun `Given a blank public key When verified Then it should be rejected with a non-blank error message`() {
        // Given
        val blankPublicKey = PublicKey(value = " ")

        // When
        val exception = assertThrows<IllegalArgumentException> {
            verifyPublicKey(blankPublicKey)
        }

        // Then
        assertEquals("Public key cannot be blank.", exception.message)
    }

    @Test
    fun `Given a public key with valid format When verified Then it should comply with Bitcoin standards`() {
        // Given
        val validFormatPublicKey = PublicKey(value = "02a84c8b9c9e4b8f8d3e76bd9f4a3b5a2b3e6a5c8d7e9f3a4b8c7d6e5f4a3b2a1")

        // When
        val result = verifyPublicKey(validFormatPublicKey)

        // Then
        assertEquals(true, result.isValid)
        assertEquals("", result.errorMessage)
    }

    @Test
    fun `Given a minimum length public key When verified Then it should be accepted as valid`() {
        // Given
        val minLengthPublicKey = PublicKey(value = "02a3b5a2b3e6a5c8d7e9f3a4b8c7d6e5f4a3b2a1")

        // When
        val result = verifyPublicKey(minLengthPublicKey)

        // Then
        assertEquals(true, result.isValid)
        assertEquals("", result.errorMessage)
    }

    @Test
    fun `Given a maximum length public key When verified Then it should be accepted as valid`() {
        // Given
        val maxLengthPublicKey = PublicKey(value = "02a3b5a2b3e6a5c8d7e9f3a4b8c7d6e5f4a3b2a1a3b5a2b3e6a5c8d7e9f3a4b8c7d6e5f4a3b2a1")

        // When
        val result = verifyPublicKey(maxLengthPublicKey)

        // Then
        assertEquals(true, result.isValid)
        assertEquals("", result.errorMessage)
    }

    @Test
    fun `Given a public key with valid hex characters When verified Then it should be accepted`() {
        // Given
        val validHexPublicKey = PublicKey(value = "02a3b5a2b3e6a5c8d7e9f3a4b8c7d6e5f4a3b2a1")

        // When
        val result = verifyPublicKey(validHexPublicKey)

        // Then
        assertEquals(true, result.isValid)
        assertEquals("", result.errorMessage)
    }

    @Test
    fun `Given a public key with invalid characters When verified Then it should be rejected with an error message`() {
        // Given
        val invalidCharPublicKey = PublicKey(value = "GHIJKLmnop1234567890!@#")

        // When
        val exception = assertThrows<IllegalArgumentException> {
            verifyPublicKey(invalidCharPublicKey)
        }

        // Then
        assertEquals("Invalid characters in public key.", exception.message)
    }

    private fun verifyPublicKey(publicKey: PublicKey): ValidationResult {
        // Mock implementation for illustration
        // Replace with actual validation logic
        return if (publicKey.value.isBlank()) {
            throw IllegalArgumentException("Public key cannot be blank.")
        } else if (publicKey.value.length < 34 || publicKey.value.length > 66) {
            throw IllegalArgumentException("Public key length is incorrect.")
        } else if (!publicKey.value.matches(Regex("^[0-9a-fA-F]+
$"))) {
            throw IllegalArgumentException("Invalid characters in public key.")
        } else {
            ValidationResult(true, "")
        }
    }

    data class ValidationResult(val isValid: Boolean, val errorMessage: String)
}
