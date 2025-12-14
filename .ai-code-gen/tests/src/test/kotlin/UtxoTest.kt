package com.example.bitcoin.tests

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import com.example.bitcoin.Utxo
import com.example.bitcoin.Address

class UtxoTest {

    @Test
    fun `Given valid UTXO parameters When creating UTXO Then instance should be created successfully`() {
        // Given
        val txId = "abc123"
        val index = 0
        val amountSats = 100000000L
        val address = Address("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa")

        // When
        val utxo = Utxo(txId, index, amountSats, address)

        // Then
        assertEquals(txId, utxo.txId)
        assertEquals(index, utxo.index)
        assertEquals(amountSats, utxo.amountSats)
        assertEquals(address, utxo.address)
    }

    @Test
    fun `Given negative sats amount When creating UTXO Then exception should be thrown`() {
        // Given
        val txId = "abc123"
        val index = 2
        val amountSats = -100000L
        val address = Address("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa")

        // Expect
        val exception = assertThrows<IllegalArgumentException> {
            Utxo(txId, index, amountSats, address)
        }

        assertEquals("Sats amount cannot be negative", exception.message)
    }

    @Test
    fun `Given maximum boundary sats amount When creating UTXO Then instance should be created successfully`() {
        // Given
        val txId = "abc123"
        val index = 1
        val amountSats = 2100000000000000L
        val address = Address("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa")

        // When
        val utxo = Utxo(txId, index, amountSats, address)

        // Then
        assertEquals(txId, utxo.txId)
        assertEquals(index, utxo.index)
        assertEquals(amountSats, utxo.amountSats)
        assertEquals(address, utxo.address)
    }

    @Test
    fun `Given invalid address format When creating UTXO Then exception should be thrown`() {
        // Given
        val txId = "abc123"
        val index = 0
        val amountSats = 500000L
        val address = Address("XYZ123")

        // Expect
        val exception = assertThrows<IllegalArgumentException> {
            Utxo(txId, index, amountSats, address)
        }

        assertEquals("Invalid Bitcoin address format", exception.message)
    }

    @Test
    fun `Given smallest index value When creating UTXO Then instance should be created successfully`() {
        // Given
        val txId = "abc123"
        val index = 0
        val amountSats = 1000L
        val address = Address("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa")

        // When
        val utxo = Utxo(txId, index, amountSats, address)

        // Then
        assertEquals(txId, utxo.txId)
        assertEquals(index, utxo.index)
        assertEquals(amountSats, utxo.amountSats)
        assertEquals(address, utxo.address)
    }

    @Test
    fun `Given largest index value When creating UTXO Then instance should be created successfully`() {
        // Given
        val txId = "xyz789"
        val index = 65535
        val amountSats = 1000L
        val address = Address("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa")

        // When
        val utxo = Utxo(txId, index, amountSats, address)

        // Then
        assertEquals(txId, utxo.txId)
        assertEquals(index, utxo.index)
        assertEquals(amountSats, utxo.amountSats)
        assertEquals(address, utxo.address)
    }

    @Test
    fun `Given blank address When creating UTXO Then exception should be thrown`() {
        // Given
        val txId = "def456"
        val index = 3
        val amountSats = 50000L
        val address = Address("")

        // Expect
        val exception = assertThrows<IllegalArgumentException> {
            Utxo(txId, index, amountSats, address)
        }

        assertEquals("Address cannot be blank", exception.message)
    }
}
