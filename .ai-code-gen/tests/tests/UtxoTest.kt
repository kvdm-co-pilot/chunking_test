package com.example.bitcoin

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class UtxoTest {

    @Test
    fun `initialization with valid data`() {
        // Given
        val txId = "validTxId1234567890abcdef"
        val index = 0
        val amountSats = 1000L
        val address = Address("validAddress")

        // When
        val utxo = Utxo(txId, index, amountSats, address)

        // Then
        assertNotNull(utxo)
        assertEquals(txId, utxo.txId)
        assertEquals(index, utxo.index)
        assertEquals(amountSats, utxo.amountSats)
        assertEquals(address, utxo.address)
    }

    @Test
    fun `handling of Utxo object with invalid txId format`() {
        // Given
        val invalidTxId = "invalidTxId!@#$%"
        val index = 0
        val amountSats = 1000L
        val address = Address("validAddress")

        // When/Then
        assertThrows(IllegalArgumentException::class.java) {
            Utxo(invalidTxId, index, amountSats, address)
        }
    }

    @Test
    fun `handling of Utxo object with negative index`() {
        // Given
        val txId = "validTxId1234567890abcdef"
        val index = -1
        val amountSats = 1000L
        val address = Address("validAddress")

        // When/Then
        assertThrows(IllegalArgumentException::class.java) {
            Utxo(txId, index, amountSats, address)
        }
    }

    @Test
    fun `handling of Utxo object with negative amountSats`() {
        // Given
        val txId = "validTxId1234567890abcdef"
        val index = 0
        val amountSats = -1000L
        val address = Address("validAddress")

        // When/Then
        assertThrows(IllegalArgumentException::class.java) {
            Utxo(txId, index, amountSats, address)
        }
    }

    @Test
    fun `handling of Utxo object with null txId`() {
        // Given
        val txId: String? = null
        val index = 0
        val amountSats = 1000L
        val address = Address("validAddress")

        // When/Then
        assertThrows(IllegalArgumentException::class.java) {
            Utxo(txId!!, index, amountSats, address)
        }
    }

    @Test
    fun `handling of Utxo object with empty txId`() {
        // Given
        val txId = ""
        val index = 0
        val amountSats = 1000L
        val address = Address("validAddress")

        // When/Then
        assertThrows(IllegalArgumentException::class.java) {
            Utxo(txId, index, amountSats, address)
        }
    }

    @Test
    fun `handling of Utxo object with null address`() {
        // Given
        val txId = "validTxId1234567890abcdef"
        val index = 0
        val amountSats = 1000L
        val address: Address? = null

        // When/Then
        assertThrows(IllegalArgumentException::class.java) {
            Utxo(txId, index, amountSats, address!!)
        }
    }

    @Test
    fun `handling of Utxo object with invalid address format`() {
        // Given
        val txId = "validTxId1234567890abcdef"
        val index = 0
        val amountSats = 1000L
        val address = Address("invalidAddressFormat")

        // When/Then
        assertThrows(IllegalArgumentException::class.java) {
            Utxo(txId, index, amountSats, address)
        }
    }

    @Test
    fun `handling of Utxo object with valid address`() {
        // Given
        val txId = "validTxId1234567890abcdef"
        val index = 0
        val amountSats = 1000L
        val address = Address("validAddress")

        // When
        val utxo = Utxo(txId, index, amountSats, address)

        // Then
        assertNotNull(utxo)
        assertEquals(txId, utxo.txId)
        assertEquals(index, utxo.index)
        assertEquals(amountSats, utxo.amountSats)
        assertEquals(address, utxo.address)
    }
}
