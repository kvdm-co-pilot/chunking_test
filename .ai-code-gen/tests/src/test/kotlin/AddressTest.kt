package com.example.bitcoin.testing

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import com.example.bitcoin.Address

class AddressTest {

    private lateinit var validBc1Address: Address
    private lateinit var valid1Address: Address
    private lateinit var valid3Address: Address

    @BeforeEach
    fun setUp() {
        validBc1Address = Address("bc1qw508d6qejxtdg4y5r3zarvary0c5xw7kygt080")
        valid1Address = Address("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa")
        valid3Address = Address("3J98t1WpEZ73CNmQviecrnyiWrnqRhWNLy")
    }

    @Test
    fun `Given a valid Bitcoin address with 'bc1' prefix, When initialized, Then it should succeed`() {
        // Given
        val expectedAddress = "bc1qw508d6qejxtdg4y5r3zarvary0c5xw7kygt080"

        // When
        val actualAddress = validBc1Address.toString()

        // Then
        assertEquals(expectedAddress, actualAddress)
    }

    @Test
    fun `Given a valid Bitcoin address with '1' prefix, When initialized, Then it should succeed`() {
        // Given
        val expectedAddress = "1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa"

        // When
        val actualAddress = valid1Address.toString()

        // Then
        assertEquals(expectedAddress, actualAddress)
    }

    @Test
    fun `Given a valid Bitcoin address with '3' prefix, When initialized, Then it should succeed`() {
        // Given
        val expectedAddress = "3J98t1WpEZ73CNmQviecrnyiWrnqRhWNLy"

        // When
        val actualAddress = valid3Address.toString()

        // Then
        assertEquals(expectedAddress, actualAddress)
    }

    @Test
    fun `Given an empty Bitcoin address, When initialized, Then it should fail with 'Address cannot be blank'`() {
        // Given
        val exceptionMessage = "Address cannot be blank"

        // When
        val exception = assertThrows<IllegalArgumentException> {
            Address("")
        }

        // Then
        assertEquals(exceptionMessage, exception.message)
    }

    @Test
    fun `Given a Bitcoin address with invalid prefix 'abc', When initialized, Then it should fail with 'Address must be a valid Bitcoin address prefix'`() {
        // Given
        val exceptionMessage = "Address must be a valid Bitcoin address prefix"

        // When
        val exception = assertThrows<IllegalArgumentException> {
            Address("abc1234567890")
        }

        // Then
        assertEquals(exceptionMessage, exception.message)
    }

    @Test
    fun `Given a Bitcoin address with minimum length, When initialized with valid prefix, Then it should succeed`() {
        // Given
        val expectedAddress = "1A"

        // When
        val actualAddress = Address("1A").toString()

        // Then
        assertEquals(expectedAddress, actualAddress)
    }

    @Test
    fun `Given a Bitcoin address with maximum length, When initialized with valid prefix, Then it should succeed`() {
        // Given
        val expectedAddress = "1" + "a".repeat(33)

        // When
        val actualAddress = Address("1" + "a".repeat(33)).toString()

        // Then
        assertEquals(expectedAddress, actualAddress)
    }

    @Test
    fun `Given a Bitcoin address with special characters, When initialized, Then it should fail if characters are invalid`() {
        // Given
        val exceptionMessage = "Address must be a valid Bitcoin address prefix"

        // When
        val exception = assertThrows<IllegalArgumentException> {
            Address("1A1zP1eP5QGe!fi2@DMPTfTL5$Lm%v7DivfNa")
        }

        // Then
        assertEquals(exceptionMessage, exception.message)
    }

    @Test
    fun `Given an empty Bitcoin address for invariant check, When initialized, Then it should fail with 'Address cannot be blank'`() {
        // Given
        val exceptionMessage = "Address cannot be blank"

        // When
        val exception = assertThrows<IllegalArgumentException> {
            Address("")
        }

        // Then
        assertEquals(exceptionMessage, exception.message)
    }
}