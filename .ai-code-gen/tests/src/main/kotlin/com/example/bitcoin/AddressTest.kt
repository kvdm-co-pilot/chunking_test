package com.example.bitcoin.tests

import com.example.bitcoin.Address
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AddressTest {

    @BeforeEach
    fun setup() {
        // No setup needed for Address tests
    }

    @Test
    fun `Functional_ValidBitcoinAddress_bc1xyz`() {
        // Given
        val address = Address("bc1xyz")

        // Then
        assertEquals("bc1xyz", address.toString())
    }

    @Test
    fun `Functional_ValidLegacyBitcoinAddress_1abc`() {
        // Given
        val address = Address("1abc")

        // Then
        assertEquals("1abc", address.toString())
    }

    @Test
    fun `Functional_ValidSegWitBitcoinAddress_bc1abc`() {
        // Given
        val address = Address("bc1abc")

        // Then
        assertEquals("bc1abc", address.toString())
    }

    @Test
    fun `Functional_ValidP2SHBitcoinAddress_3abc`() {
        // Given
        val address = Address("3abc")

        // Then
        assertEquals("3abc", address.toString())
    }

    @Test
    fun `Functional_InvalidBitcoinAddress_BlankValue`() {
        // Given
        val exception = assertThrows(IllegalArgumentException::class.java) {
            Address("")
        }

        // Then
        assertEquals("Address cannot be blank", exception.message)
    }

    @Test
    fun `Functional_InvalidBitcoinAddress_IncorrectPrefix_xx123abc`() {
        // Given
        val exception = assertThrows(IllegalArgumentException::class.java) {
            Address("xx123abc")
        }

        // Then
        assertEquals("Address must be a valid Bitcoin address prefix", exception.message)
    }

    @Test
    fun `Functional_ErrorCondition_NullAddress`() {
        // Given
        val exception = assertThrows(IllegalArgumentException::class.java) {
            Address(null as String)
        }

        // Then
        assertEquals("Address cannot be blank", exception.message)
    }

    @Test
    fun `Invariant_VerifyAddressPrefix_bc1`() {
        // Given
        val address = Address("bc1validaddress")

        // Then
        assertEquals("bc1validaddress", address.toString())
    }

    @Test
    fun `Invariant_VerifyAddressPrefix_1`() {
        // Given
        val address = Address("1validaddress")

        // Then
        assertEquals("1validaddress", address.toString())
    }

    @Test
    fun `Invariant_VerifyAddressPrefix_3`() {
        // Given
        val address = Address("3validaddress")

        // Then
        assertEquals("3validaddress", address.toString())
    }

    @Test
    fun `Invariant_VerifyInvalidAddressPrefix_xx`() {
        // Given
        val exception = assertThrows(IllegalArgumentException::class.java) {
            Address("xxinvalidaddress")
        }

        // Then
        assertEquals("Address must be a valid Bitcoin address prefix", exception.message)
    }

    @Test
    fun `DomainBoundary_MinimumLengthValidAddress_bc1q`() {
        // Given
        val address = Address("bc1q")

        // Then
        assertEquals("bc1q", address.toString())
    }

    @Test
    fun `DomainBoundary_MaximumLengthValidAddress_bc1qw508d6qejxtdg4y5r3zarvaryvg6kdaj`() {
        // Given
        val address = Address("bc1qw508d6qejxtdg4y5r3zarvaryvg6kdaj")

        // Then
        assertEquals("bc1qw508d6qejxtdg4y5r3zarvaryvg6kdaj", address.toString())
    }

    @Test
    fun `FailureRecovery_InvalidAddressHandling_Blank`() {
        // Given
        val exception = assertThrows(IllegalArgumentException::class.java) {
            Address("")
        }

        // Then
        assertEquals("Address cannot be blank", exception.message)
    }

    @Test
    fun `FailureRecovery_InvalidAddressHandling_IncorrectPrefix`() {
        // Given
        val exception = assertThrows(IllegalArgumentException::class.java) {
            Address("xx123abc")
        }

        // Then
        assertEquals("Address must be a valid Bitcoin address prefix", exception.message)
    }
}
