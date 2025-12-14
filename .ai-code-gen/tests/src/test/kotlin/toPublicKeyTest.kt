package com.example.bitcoin.testing

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import com.example.bitcoin.PrivateKey
import com.example.bitcoin.exceptions.InvalidKeyFormatException

class Com_example_bitcoin_PrivateKeyTest {

    private lateinit var privateKey: PrivateKey

    @BeforeEach
    fun setUp() {
        privateKey = PrivateKey()
    }

    @Test
    fun `Given a new PrivateKey, When generating, Then it should meet cryptographic standards and be of standard length`() {
        // Given

        // When
        val generatedKey = privateKey.generate()

        // Then
        assertTrue(generatedKey.isValid())
        assertEquals(256, generatedKey.length())
    }

    @Test
    fun `Given an invalid private key format, When storing, Then it should throw InvalidKeyFormatException with correct message`() {
        // Given
        val invalidKeyFormat = "invalidFormat"

        // When
        val exception = assertThrows<InvalidKeyFormatException> {
            privateKey.store(invalidKeyFormat)
        }

        // Then
        assertEquals("Invalid private key format", exception.message)
    }

    @Test
    fun `Given a request for private key generation, When boundary condition for key length is checked, Then it should be exactly 256 bits`() {
        // Given

        // When
        val generatedKey = privateKey.generate()

        // Then
        assertEquals(256, generatedKey.length())
    }

    @Test
    fun `Given a duplicate private key, When storing, Then it should throw DuplicateKeyException`() {
        // Given
        val duplicateKey = privateKey.generate()
        privateKey.store(duplicateKey)

        // When
        val exception = assertThrows<DuplicateKeyException> {
            privateKey.store(duplicateKey)
        }

        // Then
        assertEquals("Duplicate private key detected", exception.message)
    }

    @Test
    fun `Given a stored private key, When retrieving, Then it should be retrieved securely`() {
        // Given
        val generatedKey = privateKey.generate()
        privateKey.store(generatedKey)

        // When
        val retrievedKey = privateKey.retrieve()

        // Then
        assertEquals(generatedKey, retrievedKey)
    }
}
