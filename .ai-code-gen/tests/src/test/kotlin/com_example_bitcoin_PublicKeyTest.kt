package com.example.bitcoin.testing

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import com.example.bitcoin.PublicKey

class Com_example_bitcoin_PublicKeyTest {

    private lateinit var publicKey: PublicKey

    @BeforeEach
    fun setUp() {
        // No common setup required as each test is self-contained
    }

    @Test
    fun `Given a valid PublicKey, When storing, Then it should be stored without errors`() {
        // Given
        val validPublicKey = PublicKey(value="1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa")

        // When
        publicKey = validPublicKey

        // Then
        assertEquals(validPublicKey.value, publicKey.value)
    }

    @Test
    fun `Given an invalid PublicKey format, When storing, Then an error should be raised`() {
        // Given
        val invalidPublicKey = PublicKey(value="ZZZ123")

        // When/Then
        assertThrows<IllegalArgumentException> {
            // Attempt to store invalid public key
            publicKey = invalidPublicKey
        }.apply {
            // Verify exception message
            assertEquals("Invalid public key format", message)
        }
    }

    @Test
    fun `Given an empty PublicKey, When storing, Then an error should be raised`() {
        // Given
        val emptyPublicKey = PublicKey(value="")

        // When/Then
        assertThrows<IllegalArgumentException> {
            // Attempt to store empty public key
            publicKey = emptyPublicKey
        }.apply {
            // Verify exception message
            assertEquals("Public key cannot be empty", message)
        }
    }

    @Test
    fun `Given a PublicKey with special characters, When storing, Then an error should be raised`() {
        // Given
        val specialCharPublicKey = PublicKey(value="1A1z@P1!P5QGefi2DMPTfTL5SLmv7DivfNa")

        // When/Then
        assertThrows<IllegalArgumentException> {
            // Attempt to store public key with special characters
            publicKey = specialCharPublicKey
        }.apply {
            // Verify exception message
            assertEquals("Invalid characters in public key", message)
        }
    }

    @Test
    fun `Given an uncommon but valid PublicKey format, When storing, Then it should be stored without errors`() {
        // Given
        val uncommonValidPublicKey = PublicKey(value="bc1qar0srrr7xfkvy5l643lydnw9re59gtzzwfzt3")

        // When
        publicKey = uncommonValidPublicKey

        // Then
        assertEquals(uncommonValidPublicKey.value, publicKey.value)
    }

    @Test
    fun `Given a PublicKey at minimum length boundary, When storing, Then it should be stored without errors`() {
        // Given
        val minLengthPublicKey = PublicKey(value="bc1qw508d6qejxtdg4y5r3zarvary0c5xw7k6r5")

        // When
        publicKey = minLengthPublicKey

        // Then
        assertEquals(minLengthPublicKey.value, publicKey.value)
    }

    @Test
    fun `Given a PublicKey at maximum length boundary, When storing, Then it should be stored without errors`() {
        // Given
        val maxLengthPublicKey = PublicKey(value="bc1qw508d6qejxtdg4y5r3zarvary0c5xw7k6r5zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz")

        // When
        publicKey = maxLengthPublicKey

        // Then
        assertEquals(maxLengthPublicKey.value, publicKey.value)
    }
}
