package com.example.bitcoin.test

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import com.example.bitcoin.Address

class AddressTest {

    @BeforeEach
    fun setUp() {
        // Any setup before each test can be done here
    }

    @Test
    fun `Functional_ValidBitcoinAddress_bc1Prefix`() {
        val validBc1Address = "bc1qxy2kgdygjrsqtzq2n0yrf2493p83kkfjhx0wlh"
        val address = Address(validBc1Address)
        assertEquals(validBc1Address, address.value)
    }

    @Test
    fun `Functional_ValidBitcoinAddress_1Prefix`() {
        val valid1Address = "1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa"
        val address = Address(valid1Address)
        assertEquals(valid1Address, address.value)
    }

    @Test
    fun `Functional_ValidBitcoinAddress_3Prefix`() {
        val valid3Address = "3J98t1WpEZ73CNmQviecrnyiWrnqRhWNLy"
        val address = Address(valid3Address)
        assertEquals(valid3Address, address.value)
    }

    @Test
    fun `Functional_InvalidBitcoinAddress_BlankValue`() {
        assertThrows(IllegalArgumentException::class.java) {
            Address("")
        }
    }

    @Test
    fun `Functional_InvalidBitcoinAddress_IncorrectPrefix`() {
        val invalidAddress = "4A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa"
        assertThrows(IllegalArgumentException::class.java) {
            Address(invalidAddress)
        }
    }

    @Test
    fun `Functional_EdgeCase_MinimumValidAddressLength_bc1Prefix`() {
        val minBc1Address = "bc1q"
        val address = Address(minBc1Address)
        assertEquals(minBc1Address, address.value)
    }

    @Test
    fun `Functional_EdgeCase_MinimumValidAddressLength_1Prefix`() {
        val min1Address = "1"
        val address = Address(min1Address)
        assertEquals(min1Address, address.value)
    }

    @Test
    fun `Functional_EdgeCase_MinimumValidAddressLength_3Prefix`() {
        val min3Address = "3"
        val address = Address(min3Address)
        assertEquals(min3Address, address.value)
    }
}