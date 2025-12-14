package com.example.bitcoin.tests

import com.example.bitcoin.Utxo
import com.example.bitcoin.Address
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Disabled

class UtxoTest {

    @Test
    fun `Given valid data When creating a Utxo Then it should be created successfully`() {
        // Given
        val txId = "a3f6b8c1d2e4f7g9h0i1j2k3l4m5n6o7p8q9r0s1t2u3v4w5x6y7z8"
        val index = 0
        val amountSats = 5000000000L
        val address = Address("bc1qxy2kgdygjrsqtzq2n0yrf2493p83kkfjhx0wlh")

        // When
        val utxo = Utxo(txId, index, amountSats, address)

        // Then
        assertEquals(txId, utxo.txId)
        assertEquals(index, utxo.index)
        assertEquals(amountSats, utxo.amountSats)
        assertEquals(address, utxo.address)
    }

    @Test
    fun `Given invalid transaction ID When creating a Utxo Then it should throw an error`() {
        // Given
        val txId = "1234"
        val index = 1
        val amountSats = 1000000L
        val address = Address("1PssGeFHDnKNxiEyFrD1wcEaHr9hrQDDWc")

        // Then
        val exception = assertThrows<IllegalArgumentException> {
            // When
            Utxo(txId, index, amountSats, address)
        }
        assertEquals("Invalid transaction ID format.", exception.message)
    }

    @Test
    fun `Given negative index When creating a Utxo Then it should throw an error`() {
        // Given
        val txId = "a3f6b8c1d2e4f7g9h0i1j2k3l4m5n6o7p8q9r0s1t2u3v4w5x6y7z8"
        val index = -1
        val amountSats = 2000000L
        val address = Address("3J98t1WpEZ73CNmQviecrnyiWrnqRhWNLy")

        // Then
        val exception = assertThrows<IllegalArgumentException> {
            // When
            Utxo(txId, index, amountSats, address)
        }
        assertEquals("Index must be non-negative.", exception.message)
    }

    @Test
    fun `Given zero satoshis When creating a Utxo Then it should be flagged as a dust Utxo`() {
        // Given
        val txId = "a3f6b8c1d2e4f7g9h0i1j2k3l4m5n6o7p8q9r0s1t2u3v4w5x6y7z8"
        val index = 2
        val amountSats = 0L
        val address = Address("bc1qxy2kgdygjrsqtzq2n0yrf2493p83kkfjhx0wlh")

        // When
        val utxo = Utxo(txId, index, amountSats, address)

        // Then
        assertEquals(txId, utxo.txId)
        assertEquals(index, utxo.index)
        assertEquals(amountSats, utxo.amountSats)
        assertEquals(address, utxo.address)
        assertEquals(true, utxo.isDust())
    }

    @Test
    fun `Given excessive satoshis When creating a Utxo Then it should throw an error`() {
        // Given
        val txId = "a3f6b8c1d2e4f7g9h0i1j2k3l4m5n6o7p8q9r0s1t2u3v4w5x6y7z8"
        val index = 3
        val amountSats = 2100000000000001L
        val address = Address("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa")

        // Then
        val exception = assertThrows<IllegalArgumentException> {
            // When
            Utxo(txId, index, amountSats, address)
        }
        assertEquals("Amount exceeds maximum supply.", exception.message)
    }

    @Test
    fun `Given invalid address When creating a Utxo Then it should throw an error`() {
        // Given
        val txId = "a3f6b8c1d2e4f7g9h0i1j2k3l4m5n6o7p8q9r0s1t2u3v4w5x6y7z8"
        val index = 4
        val amountSats = 1000000L
        val address = Address("invalid_address")

        // Then
        val exception = assertThrows<IllegalArgumentException> {
            // When
            Utxo(txId, index, amountSats, address)
        }
        assertEquals("Invalid address format.", exception.message)
    }

    @Test
    fun `Given blank address When creating a Utxo Then it should throw an error`() {
        // Given
        val txId = "a3f6b8c1d2e4f7g9h0i1j2k3l4m5n6o7p8q9r0s1t2u3v4w5x6y7z8"
        val index = 5
        val amountSats = 500000L
        val address = Address("")

        // Then
        val exception = assertThrows<IllegalArgumentException> {
            // When
            Utxo(txId, index, amountSats, address)
        }
        assertEquals("Invalid address format or address is blank.", exception.message)
    }

    @Disabled("Feature not implemented")
    @Test
    fun `Given a valid private key When generating securely Then it should be secure`() {
        // Placeholder for secure private key generation test
    }

    @Disabled("Feature not implemented")
    @Test
    fun `Given transaction inputs and outputs When processed Then input-output balance should be consistent`() {
        // Placeholder for transaction input-output consistency test
    }
}