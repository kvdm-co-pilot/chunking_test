package com.example.bitcoin.testing

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import com.example.bitcoin.TransactionInput

class TransactionInputTest {

    private lateinit var transactionInput: TransactionInput

    @BeforeEach
    fun setUp() {
        // Initializing with valid transaction input
        transactionInput = TransactionInput("abcd1234", 0, "3045022100...")
    }

    @Test
    fun `Given valid inputs, When creating TransactionInput, Then it should be created with correct values`() {
        // Given
        val expectedSourceTxId = "abcd1234"
        val expectedSourceIndex = 0
        val expectedScriptSig = "3045022100..."

        // When
        val actualSourceTxId = transactionInput.sourceTxId
        val actualSourceIndex = transactionInput.sourceIndex
        val actualScriptSig = transactionInput.scriptSig

        // Then
        assertEquals(expectedSourceTxId, actualSourceTxId)
        assertEquals(expectedSourceIndex, actualSourceIndex)
        assertEquals(expectedScriptSig, actualScriptSig)
    }

    @Test
    fun `Given invalid sourceTxId, When creating TransactionInput, Then it should throw exception with correct message`() {
        // Given
        val invalidSourceTxId = "xyz789"

        // When
        val exception = assertThrows<IllegalArgumentException> {
            TransactionInput(invalidSourceTxId, 0, "3045022100...")
        }

        // Then
        assertEquals("Validation error: Transaction ID does not exist.", exception.message)
    }

    @Test
    fun `Given negative index, When creating TransactionInput, Then it should throw exception with correct message`() {
        // Given
        val negativeIndex = -1

        // When
        val exception = assertThrows<IllegalArgumentException> {
            TransactionInput("abcd1234", negativeIndex, "3045022100...")
        }

        // Then
        assertEquals("Validation error: Source index cannot be negative.", exception.message)
    }

    @Test
    fun `Given maximum index value, When creating TransactionInput, Then it should handle it correctly`() {
        // Given
        val maxIndex = 2147483647

        // When
        val transactionInputMaxIndex = TransactionInput("abcd1234", maxIndex, "3045022100...")

        // Then
        assertEquals(maxIndex, transactionInputMaxIndex.sourceIndex)
    }

    @Test
    fun `Given empty scriptSig, When creating TransactionInput, Then it should throw exception with correct message`() {
        // Given
        val emptyScriptSig = ""

        // When
        val exception = assertThrows<IllegalArgumentException> {
            TransactionInput("abcd1234", 0, emptyScriptSig)
        }

        // Then
        assertEquals("Validation error: Script signature cannot be empty.", exception.message)
    }
}
