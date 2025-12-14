package com.example.bitcoin.tests

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import com.example.bitcoin.Address
import com.example.bitcoin.InvalidCharacterException

class AddressTest {

    @Test
    fun `Given a valid Bitcoin address When converted to string Then output should match`() {
        // Given
        val validAddress = Address("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa")

        // When
        val addressString = validAddress.toString()

        // Then
        assertEquals("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa", addressString)
    }

    @Test
    fun `Given an empty Bitcoin address When converted to string Then output should be an empty string`() {
        // Given
        val emptyAddress = Address("")

        // When
        val addressString = emptyAddress.toString()

        // Then
        assertEquals("", addressString)
    }

    @Test
    fun `Given a Bitcoin address When validated Then length should be within acceptable range`() {
        // Given
        val address = Address("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa")

        // When
        val addressLength = address.toString().length

        // Then
        assert(addressLength in 26..35)
    }

    @Test
    fun `Given a malformed Bitcoin address When converted to string Then output should match`() {
        // Given
        val malformedAddress = Address("12345")

        // When
        val addressString = malformedAddress.toString()

        // Then
        assertEquals("12345", addressString)
    }

    @Test
    fun `Given a Bitcoin address When validated Then all characters should be valid base58`() {
        // Given
        val validAddress = Address("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa")
        val base58Chars = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz"

        // Then
        validAddress.toString().forEach { char ->
            assert(base58Chars.contains(char))
        }
    }

    @Test
    fun `Given a Bitcoin address with invalid characters When validated Then exception should be thrown`() {
        // Given
        val invalidAddress = Address("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNOa")

        // When/Then
        val exception = assertThrows<InvalidCharacterException> {
            invalidAddress.validate()
        }
        assertEquals("Invalid character 'O' detected.", exception.message)
    }

    @Test
    fun `Given a Bitcoin address When checksum is validated Then result should be successful`() {
        // Given
        val validAddress = Address("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa")

        // When
        val isValidChecksum = validAddress.validateChecksum()

        // Then
        assert(isValidChecksum)
    }
}
