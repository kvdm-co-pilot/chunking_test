package com.example.bitcoin

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class TransactionOutputTest {

    @Test
    fun `instantiate TransactionOutput with valid data`() {
        // Given
        val address = Address("validBitcoinAddress")
        val amountSats = 1000L
        val scriptPubKey = "validScriptPubKey"

        // When
        val transactionOutput = TransactionOutput(address, amountSats, scriptPubKey)

        // Then
        assertNotNull(transactionOutput)
        assertEquals(address, transactionOutput.address)
        assertEquals(amountSats, transactionOutput.amountSats)
        assertEquals(scriptPubKey, transactionOutput.scriptPubKey)
    }

    @Test
    fun `instantiate TransactionOutput with negative amountSats`() {
        // Given
        val address = Address("validBitcoinAddress")
        val amountSats = -1000L
        val scriptPubKey = "validScriptPubKey"

        // When & Then
        assertThrows<IllegalArgumentException> {
            TransactionOutput(address, amountSats, scriptPubKey)
        }
    }

    @Test
    fun `instantiate TransactionOutput with zero amountSats`() {
        // Given
        val address = Address("validBitcoinAddress")
        val amountSats = 0L
        val scriptPubKey = "validScriptPubKey"

        // When
        val transactionOutput = TransactionOutput(address, amountSats, scriptPubKey)

        // Then
        assertNotNull(transactionOutput)
        assertEquals(address, transactionOutput.address)
        assertEquals(amountSats, transactionOutput.amountSats)
        assertEquals(scriptPubKey, transactionOutput.scriptPubKey)
    }

    @Test
    fun `instantiate TransactionOutput with null address`() {
        // Given
        val address: Address? = null
        val amountSats = 1000L
        val scriptPubKey = "validScriptPubKey"

        // When & Then
        assertThrows<IllegalArgumentException> {
            TransactionOutput(address!!, amountSats, scriptPubKey)
        }
    }

    @Test
    fun `instantiate TransactionOutput with empty scriptPubKey`() {
        // Given
        val address = Address("validBitcoinAddress")
        val amountSats = 1000L
        val scriptPubKey = ""

        // When & Then
        assertThrows<IllegalArgumentException> {
            TransactionOutput(address, amountSats, scriptPubKey)
        }
    }
}

// Mock Address class for testing purposes
class Address(val value: String)