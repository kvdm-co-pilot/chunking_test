package com.example.bitcoin

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class UtxoTest {

    @Test
    fun `create Utxo with valid data`() {
        // Given
        val txId = "abc123"
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
    fun `handle Utxo with null txId`() {
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
    fun `handle Utxo with empty txId`() {
        // Given
        val txId = ""
        val index = 0
        val amountSats = 1000L
        val address = Address("validAddress")

        // When
        val utxo = Utxo(txId, index, amountSats, address)

        // Then
        assertNotNull(utxo)
        assertEquals(txId, utxo.txId)
    }

    @Test
    fun `handle Utxo with negative index`() {
        // Given
        val txId = "abc123"
        val index = -1
        val amountSats = 1000L
        val address = Address("validAddress")

        // When/Then
        assertThrows(IllegalArgumentException::class.java) {
            Utxo(txId, index, amountSats, address)
        }
    }

    @Test
    fun `handle Utxo with negative amountSats`() {
        // Given
        val txId = "abc123"
        val index = 0
        val amountSats = -1000L
        val address = Address("validAddress")

        // When/Then
        assertThrows(IllegalArgumentException::class.java) {
            Utxo(txId, index, amountSats, address)
        }
    }

    @Test
    fun `handle Utxo with null address`() {
        // Given
        val txId = "abc123"
        val index = 0
        val amountSats = 1000L
        val address: Address? = null

        // When/Then
        assertThrows(IllegalArgumentException::class.java) {
            Utxo(txId, index, amountSats, address!!)
        }
    }
}

// Assuming Address is a simple class with a single String property
data class Address(val value: String)