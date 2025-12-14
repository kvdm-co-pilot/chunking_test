package com.example.bitcoin.testing

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import com.example.bitcoin.TransactionInput

class Com_example_bitcoin_TransactionInputTest {

    private lateinit var transactionInput: TransactionInput

    @BeforeEach
    fun setUp() {
        // Common setup if needed
    }

    @Test
    fun `Given valid inputs, When creating TransactionInput, Then it should be initialized successfully`() {
        // Given
        val sourceTxId = "abc123"
        val sourceIndex = 0
        val scriptSig = "3045022100c0decafe"

        // When
        transactionInput = TransactionInput(sourceTxId, sourceIndex, scriptSig)

        // Then
        assertEquals(sourceTxId, transactionInput.sourceTxId)
        assertEquals(sourceIndex, transactionInput.sourceIndex)
        assertEquals(scriptSig, transactionInput.scriptSig)
    }

    @Test
    fun `Given invalid transaction ID, When creating TransactionInput, Then it should throw an error`() {
        // Given
        val sourceTxId = "invalidTxId"
        val sourceIndex = 0
        val scriptSig = "3045022100c0decafe"

        // When
        val exception = assertThrows<IllegalArgumentException> {
            TransactionInput(sourceTxId, sourceIndex, scriptSig)
        }

        // Then
        assertEquals("Transaction ID is invalid", exception.message)
    }

    @Test
    fun `Given negative source index, When creating TransactionInput, Then it should throw an error`() {
        // Given
        val sourceTxId = "abc123"
        val sourceIndex = -1
        val scriptSig = "3045022100c0decafe"

        // When
        val exception = assertThrows<IllegalArgumentException> {
            TransactionInput(sourceTxId, sourceIndex, scriptSig)
        }

        // Then
        assertEquals("Source index must be non-negative", exception.message)
    }

    @Test
    fun `Given empty script signature, When creating TransactionInput, Then it should throw an error`() {
        // Given
        val sourceTxId = "abc123"
        val sourceIndex = 0
        val scriptSig = ""

        // When
        val exception = assertThrows<IllegalArgumentException> {
            TransactionInput(sourceTxId, sourceIndex, scriptSig)
        }

        // Then
        assertEquals("Script signature cannot be empty", exception.message)
    }

    @Test
    fun `Given maximum source index, When creating TransactionInput, Then it should be initialized successfully`() {
        // Given
        val sourceTxId = "abc123"
        val sourceIndex = 2
        val scriptSig = "3045022100c0decafe"

        // When
        transactionInput = TransactionInput(sourceTxId, sourceIndex, scriptSig)

        // Then
        assertEquals(sourceTxId, transactionInput.sourceTxId)
        assertEquals(sourceIndex, transactionInput.sourceIndex)
        assertEquals(scriptSig, transactionInput.scriptSig)
    }

    @Test
    fun `Given source index exceeding maximum, When creating TransactionInput, Then it should throw an error`() {
        // Given
        val sourceTxId = "abc123"
        val sourceIndex = 4
        val scriptSig = "3045022100c0decafe"

        // When
        val exception = assertThrows<IllegalArgumentException> {
            TransactionInput(sourceTxId, sourceIndex, scriptSig)
        }

        // Then
        assertEquals("Source index exceeds available outputs", exception.message)
    }
}
