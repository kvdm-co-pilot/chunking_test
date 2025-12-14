package com.example.bitcoin.test

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import com.example.bitcoin.Address

class AddressTest {
    private lateinit var validBitcoinAddress: Address
    private lateinit var invalidBitcoinAddress: Address
    private lateinit var emptyBitcoinAddress: Address
    private lateinit var maxLengthBitcoinAddress: Address
    private lateinit var minLengthBitcoinAddress: Address

    @BeforeEach
    fun setUp() {
        validBitcoinAddress = Address("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa")
        invalidBitcoinAddress = Address("InvalidAddress123")
        emptyBitcoinAddress = Address("")
        maxLengthBitcoinAddress = Address("bc1qar0srrr7xfkvy5l643lydnw9re59gtzzwf5mdq")
        minLengthBitcoinAddress = Address("3J98t1WpEZ73CNmQviecrnyiWrnqRhWNLy")
    }

    @Test
    fun `Functional_ValidBitcoinAddressConversion_GivenValidAddress_ReturnsExpectedString`() {
        assertEquals("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa", validBitcoinAddress.toString())
    }

    @Test
    fun `Functional_InvalidBitcoinAddressHandling_GivenInvalidAddress_ReturnsErrorOrNull`() {
        assertNull(invalidBitcoinAddress.toString())
    }

    @Test
    fun `Functional_EmptyBitcoinAddressHandling_GivenEmptyAddress_ReturnsErrorOrEmptyString`() {
        assertEquals("", emptyBitcoinAddress.toString())
    }

    @Test
    fun `Functional_MaximumLengthBitcoinAddress_GivenMaxLengthAddress_ReturnsCompleteString`() {
        assertEquals("bc1qar0srrr7xfkvy5l643lydnw9re59gtzzwf5mdq", maxLengthBitcoinAddress.toString())
    }

    @Test
    fun `Functional_MinimumLengthBitcoinAddress_GivenMinLengthAddress_ReturnsCompleteString`() {
        assertEquals("3J98t1WpEZ73CNmQviecrnyiWrnqRhWNLy", minLengthBitcoinAddress.toString())
    }

    @Test
    fun `Functional_NullBitcoinAddressHandling_GivenNullAddress_ReturnsErrorOrNull`() {
        val nullBitcoinAddress = Address(null)
        assertNull(nullBitcoinAddress.toString())
    }

    @Test
    fun `Invariant_BitcoinAddressValidity_GivenInvalidAddress_ReturnsErrorOrNull`() {
        val invalidAddress = Address("Invalid123456")
        assertNull(invalidAddress.toString())
    }

    @Test
    fun `Invariant_BitcoinAddressValidity_GivenValidAddress_ReturnsExpectedString`() {
        val validAddress = Address("1BoatSLRHtKNngkdXEeobR76b53LETtpyT")
        assertEquals("1BoatSLRHtKNngkdXEeobR76b53LETtpyT", validAddress.toString())
    }
}
