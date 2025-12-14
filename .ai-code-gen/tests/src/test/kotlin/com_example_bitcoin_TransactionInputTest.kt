package com.example.bitcoin.tests

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import com.example.bitcoin.TransactionInput

class TransactionInputTest {

    @Test
    fun `Given valid TransactionInput When processed Then transaction should be accepted`() {
        // Given
        val sourceTxId = "abc123"
        val sourceIndex = 1
        val scriptSig = "304502..."

        // When
        val transactionInput = TransactionInput(
            sourceTxId = sourceTxId,
            sourceIndex = sourceIndex,
            scriptSig = scriptSig
        )

        // Then
        assertEquals("abc123", transactionInput.sourceTxId)
        assertEquals(1, transactionInput.sourceIndex)
        assertEquals("304502...", transactionInput.scriptSig)
    }

    @Test
    fun `Given nonexistent sourceTxId When processed Then error should be raised`() {
        // Given
        val sourceTxId = "nonexistentTxId"
        val sourceIndex = 1
        val scriptSig = "304502..."

        // When
        val exception = assertThrows<IllegalArgumentException>{
            TransactionInput(
                sourceTxId = sourceTxId,
                sourceIndex = sourceIndex,
                scriptSig = scriptSig
            )
        }

        // Then
        assertEquals("Source transaction ID does not exist.", exception.message)
    }

    @Test
    fun `Given invalid sourceIndex When processed Then error should be raised`() {
        // Given
        val sourceTxId = "abc123"
        val sourceIndex = -1
        val scriptSig = "304502..."

        // When
        val exception = assertThrows<IllegalArgumentException>{
            TransactionInput(
                sourceTxId = sourceTxId,
                sourceIndex = sourceIndex,
                scriptSig = scriptSig
            )
        }

        // Then
        assertEquals("Source index is out of bounds.", exception.message)
    }

    @Test
    fun `Given invalid scriptSig When processed Then error should be raised`() {
        // Given
        val sourceTxId = "abc123"
        val sourceIndex = 1
        val scriptSig = "invalidSig"

        // When
        val exception = assertThrows<IllegalArgumentException>{
            TransactionInput(
                sourceTxId = sourceTxId,
                sourceIndex = sourceIndex,
                scriptSig = scriptSig
            )
        }

        // Then
        assertEquals("Script signature is invalid.", exception.message)
    }

    @Test
    fun `Given maximum sourceIndex When processed Then transaction should be accepted`() {
        // Given
        val sourceTxId = "abc123"
        val sourceIndex = 4294967295
        val scriptSig = "304502..."

        // When
        val transactionInput = TransactionInput(
            sourceTxId = sourceTxId,
            sourceIndex = sourceIndex,
            scriptSig = scriptSig
        )

        // Then
        assertEquals("abc123", transactionInput.sourceTxId)
        assertEquals(4294967295, transactionInput.sourceIndex)
        assertEquals("304502...", transactionInput.scriptSig)
    }

    @Test
    fun `Given minimum sourceIndex When processed Then transaction should be accepted`() {
        // Given
        val sourceTxId = "abc123"
        val sourceIndex = 0
        val scriptSig = "304502..."

        // When
        val transactionInput = TransactionInput(
            sourceTxId = sourceTxId,
            sourceIndex = sourceIndex,
            scriptSig = scriptSig
        )

        // Then
        assertEquals("abc123", transactionInput.sourceTxId)
        assertEquals(0, transactionInput.sourceIndex)
        assertEquals("304502...", transactionInput.scriptSig)
    }

    @Test
    fun `Given blank sourceTxId When processed Then error should be raised`() {
        // Given
        val sourceTxId = ""
        val sourceIndex = 1
        val scriptSig = "304502..."

        // When
        val exception = assertThrows<IllegalArgumentException>{
            TransactionInput(
                sourceTxId = sourceTxId,
                sourceIndex = sourceIndex,
                scriptSig = scriptSig
            )
        }

        // Then
        assertEquals("Source transaction ID cannot be blank.", exception.message)
    }

    @Test
    fun `Given valid TransactionInput When processed Then it should contribute to valid economics`() {
        // Given
        val sourceTxId = "abc123"
        val sourceIndex = 1
        val scriptSig = "304502..."

        // When
        val transactionInput = TransactionInput(
            sourceTxId = sourceTxId,
            sourceIndex = sourceIndex,
            scriptSig = scriptSig
        )

        // Then
        assertTrue(transactionInput.sourceIndex > 0) // Simplified for illustration
    }
}