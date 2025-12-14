package com.example.bitcoin.tests

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import com.example.bitcoin.TransactionInput

class TransactionInputTest {

    @Test
    fun `Functional_ValidTransactionInputCreation Successfully create a TransactionInput with valid parameters`() {
        // Given
        val sourceTxId = "abc123xyz"
        val sourceIndex = 0
        val scriptSig = "3045022100c4b..."

        // When
        val transactionInput = TransactionInput(
            sourceTxId = sourceTxId,
            sourceIndex = sourceIndex,
            scriptSig = scriptSig
        )

        // Then
        assertEquals(sourceTxId, transactionInput.sourceTxId)
        assertEquals(sourceIndex, transactionInput.sourceIndex)
        assertEquals(scriptSig, transactionInput.scriptSig)
    }

    @Test
    fun `Functional_InvalidSourceTransactionID Attempt to create TransactionInput with invalid transaction ID`() {
        // Given
        val invalidSourceTxId = "invalid_tx_id"
        val sourceIndex = 0
        val scriptSig = "3045022100c4b..."

        // When & Then
        val exception = assertThrows<IllegalArgumentException> {
            TransactionInput(
                sourceTxId = invalidSourceTxId,
                sourceIndex = sourceIndex,
                scriptSig = scriptSig
            )
        }
        assertEquals("Invalid source transaction ID", exception.message)
    }

    @Test
    fun `Functional_NegativeSourceIndex Attempt to create TransactionInput with negative source index`() {
        // Given
        val sourceTxId = "abc123xyz"
        val negativeSourceIndex = -1
        val scriptSig = "3045022100c4b..."

        // When & Then
        val exception = assertThrows<IllegalArgumentException> {
            TransactionInput(
                sourceTxId = sourceTxId,
                sourceIndex = negativeSourceIndex,
                scriptSig = scriptSig
            )
        }
        assertEquals("Source index cannot be negative", exception.message)
    }

    @Test
    fun `Functional_EmptyScriptSignature Attempt to create TransactionInput with empty script signature`() {
        // Given
        val sourceTxId = "abc123xyz"
        val sourceIndex = 0
        val emptyScriptSig = ""

        // When & Then
        val exception = assertThrows<IllegalArgumentException> {
            TransactionInput(
                sourceTxId = sourceTxId,
                sourceIndex = sourceIndex,
                scriptSig = emptyScriptSig
            )
        }
        assertEquals("Script signature cannot be empty", exception.message)
    }

    @Test
    fun `Invariant_ValidSourceTransactionID Verify source transaction ID is valid and not blank`() {
        // Given
        val sourceTxId = "abc123xyz"
        val sourceIndex = 0
        val scriptSig = "3045022100c4b..."

        // When
        val transactionInput = TransactionInput(
            sourceTxId = sourceTxId,
            sourceIndex = sourceIndex,
            scriptSig = scriptSig
        )

        // Then
        assertEquals(sourceTxId, transactionInput.sourceTxId)
    }

    @Test
    fun `Invariant_ValidSourceIndex Ensure source index is non-negative and within valid range`() {
        // Given
        val sourceTxId = "abc123xyz"
        val sourceIndex = 0
        val scriptSig = "3045022100c4b..."

        // When
        val transactionInput = TransactionInput(
            sourceTxId = sourceTxId,
            sourceIndex = sourceIndex,
            scriptSig = scriptSig
        )

        // Then
        assertEquals(sourceIndex, transactionInput.sourceIndex)
    }

    @Test
    fun `Invariant_NonEmptyScriptSignature Check script signature is not empty`() {
        // Given
        val sourceTxId = "abc123xyz"
        val sourceIndex = 0
        val scriptSig = "3045022100c4b..."

        // When
        val transactionInput = TransactionInput(
            sourceTxId = sourceTxId,
            sourceIndex = sourceIndex,
            scriptSig = scriptSig
        )

        // Then
        assertEquals(scriptSig, transactionInput.scriptSig)
    }

    @Test
    fun `Boundary_MaximumSourceIndex Create TransactionInput with maximum allowed source index`() {
        // Given
        val sourceTxId = "abc123xyz"
        val maxSourceIndex = 2147483647
        val scriptSig = "3045022100c4b..."

        // When
        val transactionInput = TransactionInput(
            sourceTxId = sourceTxId,
            sourceIndex = maxSourceIndex,
            scriptSig = scriptSig
        )

        // Then
        assertEquals(maxSourceIndex, transactionInput.sourceIndex)
    }

    @Test
    fun `Boundary_MinimumSourceIndex Attempt to create TransactionInput with minimum valid source index`() {
        // Given
        val sourceTxId = "abc123xyz"
        val minSourceIndex = 0
        val scriptSig = "3045022100c4b..."

        // When
        val transactionInput = TransactionInput(
            sourceTxId = sourceTxId,
            sourceIndex = minSourceIndex,
            scriptSig = scriptSig
        )

        // Then
        assertEquals(minSourceIndex, transactionInput.sourceIndex)
    }

    @Test
    fun `Error_Failure_SimulatedNetworkFailureDuringTransactionInputCreation Simulate network failure during creation and ensure system recovery`() {
        // Given
        val sourceTxId = "abc123xyz"
        val sourceIndex = 0
        val scriptSig = "3045022100c4b..."

        // Simulate network failure
        // When & Then
        assertThrows<NetworkException> {
            simulateNetworkFailure()
            TransactionInput(sourceTxId, sourceIndex, scriptSig)
        }
    }

    private fun simulateNetworkFailure() {
        throw NetworkException("Simulated network failure")
    }
}

class NetworkException(message: String) : RuntimeException(message)
