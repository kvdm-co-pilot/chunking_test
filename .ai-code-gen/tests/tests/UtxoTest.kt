package com.example.bitcoin

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class UtxoTest {

    @Test
    fun `create Utxo with valid data`() {
        // Given
        val txId = "abc123"
        val index = 0
        val amountSats = 1000L
        val address = Address("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa")

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
    fun `handle Utxo creation with empty txId`() {
        // Given
        val txId = ""
        val index = 0
        val amountSats = 1000L
        val address = Address("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa")

        // When/Then
        val exception = assertThrows<IllegalArgumentException> {
            Utxo(txId, index, amountSats, address)
        }
        assertEquals("Invalid txId", exception.message)
    }

    @Test
    fun `handle Utxo creation with negative index`() {
        // Given
        val txId = "abc123"
        val index = -1
        val amountSats = 1000L
        val address = Address("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa")

        // When/Then
        val exception = assertThrows<IllegalArgumentException> {
            Utxo(txId, index, amountSats, address)
        }
        assertEquals("Invalid index", exception.message)
    }

    @Test
    fun `handle Utxo creation with negative amountSats`() {
        // Given
        val txId = "abc123"
        val index = 0
        val amountSats = -1000L
        val address = Address("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa")

        // When/Then
        val exception = assertThrows<IllegalArgumentException> {
            Utxo(txId, index, amountSats, address)
        }
        assertEquals("Invalid amountSats", exception.message)
    }

    @Test
    fun `handle Utxo creation with null address`() {
        // Given
        val txId = "abc123"
        val index = 0
        val amountSats = 1000L
        val address = null

        // When/Then
        val exception = assertThrows<IllegalArgumentException> {
            Utxo(txId, index, amountSats, address!!)
        }
        assertEquals("Invalid address", exception.message)
    }

    @Test
    fun `create Utxo with maximum amountSats`() {
        // Given
        val txId = "abc123"
        val index = 0
        val amountSats = 2100000000000000L
        val address = Address("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa")

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
    fun `create Utxo with minimum index`() {
        // Given
        val txId = "abc123"
        val index = 0
        val amountSats = 1000L
        val address = Address("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa")

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
    fun `create Utxo with large index value`() {
        // Given
        val txId = "abc123"
        val index = 999999
        val amountSats = 1000L
        val address = Address("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa")

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
    fun `create Utxo with valid address format`() {
        // Given
        val txId = "abc123"
        val index = 0
        val amountSats = 1000L
        val address = Address("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa")

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
