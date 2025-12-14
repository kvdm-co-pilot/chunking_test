package com.example.bitcoin.tests

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import com.example.bitcoin.TransactionOutput
import com.example.bitcoin.Address

class TransactionOutputTest {

    @Test
    fun `Functional_ValidTransactionOutput - Valid Transaction Output`() {
        // Given
        val address = Address("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa")
        val amountSats = 100000L
        val scriptPubKey = "76a91488ac"

        // When
        val transactionOutput = TransactionOutput(address, amountSats, scriptPubKey)

        // Then
        assertEquals(address, transactionOutput.address)
        assertEquals(amountSats, transactionOutput.amountSats)
        assertEquals(scriptPubKey, transactionOutput.scriptPubKey)
    }

    @Test
    fun `Functional_ZeroAmountTransactionOutput - Zero Amount Transaction Output`() {
        // Given
        val address = Address("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa")
        val amountSats = 0L
        val scriptPubKey = "76a91488ac"

        // When
        val exception = assertThrows<IllegalArgumentException> {
            TransactionOutput(address, amountSats, scriptPubKey)
        }

        // Then
        assertEquals("Transaction amount cannot be zero.", exception.message)
    }

    @Test
    fun `Functional_NegativeAmountTransactionOutput - Negative Amount Transaction Output`() {
        // Given
        val address = Address("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa")
        val amountSats = -1000L
        val scriptPubKey = "76a91488ac"

        // When
        val exception = assertThrows<IllegalArgumentException> {
            TransactionOutput(address, amountSats, scriptPubKey)
        }

        // Then
        assertEquals("Transaction amount cannot be negative.", exception.message)
    }

    @Test
    fun `Functional_InvalidBitcoinAddress - Invalid Bitcoin Address`() {
        // Given
        val address = Address("12345")
        val amountSats = 50000L
        val scriptPubKey = "76a91488ac"

        // When
        val exception = assertThrows<IllegalArgumentException> {
            TransactionOutput(address, amountSats, scriptPubKey)
        }

        // Then
        assertEquals("Invalid Bitcoin address format.", exception.message)
    }

    @Test
    fun `Functional_InvalidScriptPubKey - Invalid ScriptPubKey`() {
        // Given
        val address = Address("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa")
        val amountSats = 50000L
        val scriptPubKey = "00"

        // When
        val exception = assertThrows<IllegalArgumentException> {
            TransactionOutput(address, amountSats, scriptPubKey)
        }

        // Then
        assertEquals("Invalid scriptPubKey format.", exception.message)
    }

    @Test
    fun `Functional_MaximumAmountSatoshis - Maximum Amount Satoshis`() {
        // Given
        val address = Address("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa")
        val amountSats = 2100000000000L
        val scriptPubKey = "76a91488ac"

        // When
        val transactionOutput = TransactionOutput(address, amountSats, scriptPubKey)

        // Then
        assertEquals(address, transactionOutput.address)
        assertEquals(amountSats, transactionOutput.amountSats)
        assertEquals(scriptPubKey, transactionOutput.scriptPubKey)
    }

    @Test
    fun `Functional_MinimumAmountSatoshis - Minimum Amount Satoshis`() {
        // Given
        val address = Address("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa")
        val amountSats = 1L
        val scriptPubKey = "76a91488ac"

        // When
        val transactionOutput = TransactionOutput(address, amountSats, scriptPubKey)

        // Then
        assertEquals(address, transactionOutput.address)
        assertEquals(amountSats, transactionOutput.amountSats)
        assertEquals(scriptPubKey, transactionOutput.scriptPubKey)
    }
}
