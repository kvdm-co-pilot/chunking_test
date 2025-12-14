package com.example.bitcoin.tests

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import com.example.bitcoin.Address

class AddressTest {

    @Test
    fun `Given a valid Bitcoin address When instantiated Then address should be accepted`() {
        // Given
        val validAddress = "bc1qxy2kgdygjrsqtzq2n0yrf2493p83kkfjhx0wlh"

        // When
        val address = Address(validAddress)

        // Then
        assertEquals(validAddress, address.value)
    }

    @Test
    fun `Given an invalid Bitcoin address prefix When instantiated Then should throw exception with correct message`() {
        // Given
        val invalidAddress = "x1qxy2kgdygjrsqtzq2n0yrf2493p83kkfjhx0wlh"

        // When & Then
        val exception = assertThrows<IllegalArgumentException> {
            Address(invalidAddress)
        }

        assertEquals("Address must be a valid Bitcoin address prefix", exception.message)
    }

    @Test
    fun `Given a blank Bitcoin address When instantiated Then should throw exception with correct message`() {
        // Given
        val blankAddress = ""

        // When & Then
        val exception = assertThrows<IllegalArgumentException> {
            Address(blankAddress)
        }

        assertEquals("Address cannot be blank", exception.message)
    }

    @Test
    fun `Given the shortest valid Bitcoin address When instantiated Then should be accepted`() {
        // Given
        val minimumValidAddress = "1A"

        // When
        val address = Address(minimumValidAddress)

        // Then
        assertEquals(minimumValidAddress, address.value)
    }

    @Test
    fun `Given the longest valid Bitcoin address When instantiated Then should be accepted`() {
        // Given
        val maximumValidAddress = "bc1qw508d6qejxtdg4y5r3zarvary0c5xw7k8z8q3t"

        // When
        val address = Address(maximumValidAddress)

        // Then
        assertEquals(maximumValidAddress, address.value)
    }

    @Test
    fun `Given an excessively long Bitcoin address When instantiated Then should throw exception with correct message`() {
        // Given
        val excessivelyLongAddress = "bc1qw508d6qejxtdg4y5r3zarvary0c5xw7k8z8q3t000000000000000000000"

        // When & Then
        val exception = assertThrows<IllegalArgumentException> {
            Address(excessivelyLongAddress)
        }

        assertEquals("Address exceeds valid length", exception.message)
    }

    @Test
    fun `Given a Bitcoin address with non-ASCII characters When instantiated Then should throw exception with correct message`() {
        // Given
        val nonAsciiAddress = "bc1你好"

        // When & Then
        val exception = assertThrows<IllegalArgumentException> {
            Address(nonAsciiAddress)
        }

        assertEquals("Address contains invalid characters", exception.message)
    }
}
