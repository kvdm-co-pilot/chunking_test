package com.example.bitcoin.tests

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import com.example.bitcoin.TransactionInput

class TransactionInputTest {

    @Test
    fun `Given valid TransactionInput When created Then fields should match the input values`() {
        // Given
        val expectedSourceTxId = "abc123"
        val expectedSourceIndex = 0
        val expectedScriptSig = "3045022100..."

        // When
        val transactionInput = TransactionInput(
            sourceTxId = expectedSourceTxId,
            sourceIndex = expectedSourceIndex,
            scriptSig = expectedScriptSig
        )

        // Then
        assertEquals(expectedSourceTxId, transactionInput.sourceTxId)
        assertEquals(expectedSourceIndex, transactionInput.sourceIndex)
        assertEquals(expectedScriptSig, transactionInput.scriptSig)
    }

    @Test
    fun `Given invalid sourceTxId When creating TransactionInput Then throws exception with appropriate message`() {
        // Given
        val invalidSourceTxId = "invalidTx"
        val sourceIndex = 0
        val scriptSig = "3045022100..."

        // When & Then
        val exception = assertThrows(IllegalArgumentException::class.java) {
            TransactionInput(
                sourceTxId = invalidSourceTxId,
                sourceIndex = sourceIndex,
                scriptSig = scriptSig
            )
        }
        assertEquals("Source transaction ID does not exist.", exception.message)
    }

    @Test
    fun `Given invalid sourceIndex When creating TransactionInput Then throws exception with appropriate message`() {
        // Given
        val sourceTxId = "abc123"
        val invalidSourceIndex = 999
        val scriptSig = "3045022100..."

        // When & Then
        val exception = assertThrows(IndexOutOfBoundsException::class.java) {
            TransactionInput(
                sourceTxId = sourceTxId,
                sourceIndex = invalidSourceIndex,
                scriptSig = scriptSig
            )
        }
        assertEquals("Index is out of bounds.", exception.message)
    }

    @Test
    fun `Given minimal valid scriptSig When creating TransactionInput Then creation is successful`() {
        // Given
        val sourceTxId = "abc123"
        val sourceIndex = 0
        val minimalScriptSig = "3045022100..."

        // When
        val transactionInput = TransactionInput(
            sourceTxId = sourceTxId,
            sourceIndex = sourceIndex,
            scriptSig = minimalScriptSig
        )

        // Then
        assertEquals(sourceTxId, transactionInput.sourceTxId)
        assertEquals(sourceIndex, transactionInput.sourceIndex)
        assertEquals(minimalScriptSig, transactionInput.scriptSig)
    }

    @Test
    fun `Given empty scriptSig When creating TransactionInput Then throws exception with appropriate message`() {
        // Given
        val sourceTxId = "abc123"
        val sourceIndex = 0
        val emptyScriptSig = ""

        // When & Then
        val exception = assertThrows(IllegalArgumentException::class.java) {
            TransactionInput(
                sourceTxId = sourceTxId,
                sourceIndex = sourceIndex,
                scriptSig = emptyScriptSig
            )
        }
        assertEquals("scriptSig is required.", exception.message)
    }

    @Test
    fun `Given complex scriptSig When creating TransactionInput Then creation is successful`() {
        // Given
        val sourceTxId = "abc123"
        val sourceIndex = 0
        val complexScriptSig = "3045022100...complex..."

        // When
        val transactionInput = TransactionInput(
            sourceTxId = sourceTxId,
            sourceIndex = sourceIndex,
            scriptSig = complexScriptSig
        )

        // Then
        assertEquals(sourceTxId, transactionInput.sourceTxId)
        assertEquals(sourceIndex, transactionInput.sourceIndex)
        assertEquals(complexScriptSig, transactionInput.scriptSig)
    }
}