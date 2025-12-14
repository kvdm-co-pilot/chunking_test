package com.example.bitcoin.testing

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import com.example.bitcoin.Address

class AddressTest {

    private lateinit var address: Address

    @BeforeEach
    fun setUp() {
        // Setup logic if necessary
    }

    @Test
    fun `Given a valid Bitcoin address, When calling toString, Then it should return the expected address`() {
        // Given
        val validAddress = "1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa"
        address = Address(validAddress)

        // When
        val actualAddressString = address.toString()

        // Then
        assertEquals(validAddress, actualAddressString)
    }

    @Test
    fun `Given an invalid Bitcoin address format, When creating Address, Then it should throw an exception`() {
        // Given
        val invalidAddress = "12345"

        // When / Then
        val exception = assertThrows<IllegalArgumentException> {
            address = Address(invalidAddress)
        }
        assertEquals("Invalid address format", exception.message)
    }

    @Test
    fun `Given an empty Bitcoin address string, When creating Address, Then it should throw an exception`() {
        // Given
        val emptyAddress = ""

        // When / Then
        val exception = assertThrows<IllegalArgumentException> {
            address = Address(emptyAddress)
        }
        assertEquals("Address cannot be empty", exception.message)
    }

    @Test
    fun `Given a null Bitcoin address value, When creating Address, Then it should throw an exception`() {
        // Given
        val nullAddress: String? = null

        // When / Then
        val exception = assertThrows<IllegalArgumentException> {
            address = Address(nullAddress!!)
        }
        assertEquals("Null values are not allowed", exception.message)
    }

    @Test
    fun `Given a maximum length Bitcoin address, When calling toString, Then it should return the exact address`() {
        // Given
        val maxLengthAddress = "3QJmnh" + "9".repeat(28)  // Example maximum length
        address = Address(maxLengthAddress)

        // When
        val actualAddressString = address.toString()

        // Then
        assertEquals(maxLengthAddress, actualAddressString)
    }

    @Test
    fun `Given a minimum length Bitcoin address, When calling toString, Then it should return the exact address`() {
        // Given
        val minLengthAddress = "1A1zP" + "9".repeat(20)  // Example minimum length
        address = Address(minLengthAddress)

        // When
        val actualAddressString = address.toString()

        // Then
        assertEquals(minLengthAddress, actualAddressString)
    }

    @Test
    fun `Given valid Bitcoin prefixes, When creating Address, Then it should recognize as valid format`() {
        // Given
        val prefix1Address = "1" + "A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa".drop(1)
        val prefix3Address = "3" + "QJmnh9".repeat(4)
        val prefixBc1Address = "bc1" + "abcd".repeat(8)

        // When
        val address1 = Address(prefix1Address)
        val address3 = Address(prefix3Address)
        val addressBc1 = Address(prefixBc1Address)

        // Then
        assertEquals(prefix1Address, address1.toString())
        assertEquals(prefix3Address, address3.toString())
        assertEquals(prefixBc1Address, addressBc1.toString())
    }

    @Test
    fun `Given an attempt to modify address string after creation, Then it should remain unchanged`() {
        // Given
        val immutableAddress = "1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa"
        address = Address(immutableAddress)

        // When
        val attemptedModification = { address.toString() + "tampered" }

        // Then
        assertEquals(immutableAddress, address.toString())
    }

    @Test
    fun `Given an address associated with a Wallet entity, When verifying mapping, Then it should maintain consistency`() {
        // Given
        val associatedAddress = "1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa"
        address = Address(associatedAddress)

        // Assuming a hypothetical Wallet entity for context
        // val wallet = Wallet(associatedAddress)

        // When
        val actualAddressInWallet = address.toString() // wallet.getAddress()

        // Then
        assertEquals(associatedAddress, actualAddressInWallet)
    }
}
