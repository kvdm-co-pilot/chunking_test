package com.example.bitcoin

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class TransactionOutputTest {

    @Test
    fun `instantiate TransactionOutput with valid values`() {
        // Given
        val validAddress = Address("validBitcoinAddress")
        val positiveAmountSats = 1000L
        val validScriptPubKey = "validScriptPubKey"

        // When
        val transactionOutput = TransactionOutput(validAddress, positiveAmountSats, validScriptPubKey)

        // Then
        assertNotNull(transactionOutput)
        assertEquals(validAddress, transactionOutput.address)
        assertEquals(positiveAmountSats, transactionOutput.amountSats)
        assertEquals(validScriptPubKey, transactionOutput.scriptPubKey)
    }

    @Test
    fun `handle negative amountSats`() {
        // Given
        val validAddress = Address("validBitcoinAddress")
        val negativeAmountSats = -1000L
        val validScriptPubKey = "validScriptPubKey"

        // When
        val exception = assertThrows(IllegalArgumentException::class.java) {
            TransactionOutput(validAddress, negativeAmountSats, validScriptPubKey)
        }

        // Then
        assertEquals("Invalid amount", exception.message)
    }

    @Test
    fun `handle zero amountSats`() {
        // Given
        val validAddress = Address("validBitcoinAddress")
        val zeroAmountSats = 0L
        val validScriptPubKey = "validScriptPubKey"

        // When
        val exception = assertThrows(IllegalArgumentException::class.java) {
            TransactionOutput(validAddress, zeroAmountSats, validScriptPubKey)
        }

        // Then
        assertEquals("Invalid amount", exception.message)
    }

    @Test
    fun `handle null address`() {
        // Given
        val nullAddress: Address? = null
        val positiveAmountSats = 1000L
        val validScriptPubKey = "validScriptPubKey"

        // When
        val exception = assertThrows(IllegalArgumentException::class.java) {
            TransactionOutput(nullAddress!!, positiveAmountSats, validScriptPubKey)
        }

        // Then
        assertEquals("Invalid address", exception.message)
    }

    @Test
    fun `handle empty scriptPubKey`() {
        // Given
        val validAddress = Address("validBitcoinAddress")
        val positiveAmountSats = 1000L
        val emptyScriptPubKey = ""

        // When
        val exception = assertThrows(IllegalArgumentException::class.java) {
            TransactionOutput(validAddress, positiveAmountSats, emptyScriptPubKey)
        }

        // Then
        assertEquals("Invalid scriptPubKey", exception.message)
    }
}
