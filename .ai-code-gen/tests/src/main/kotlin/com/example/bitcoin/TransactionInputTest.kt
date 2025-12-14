package com.example.bitcoin.tests

import com.example.bitcoin.TransactionInput
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class TransactionInputTest {

    @Test
    fun `Given valid sourceTxId, sourceIndex and scriptSig, When a TransactionInput is created, Then it should store correct values`() {
        // Given
        val sourceTxId = "abc123"
        val sourceIndex = 0
        val scriptSig = "3045022100"

        // When
        val transactionInput = TransactionInput(sourceTxId, sourceIndex, scriptSig)

        // Then
        assertEquals(sourceTxId, transactionInput.sourceTxId)
        assertEquals(sourceIndex, transactionInput.sourceIndex)
        assertEquals(scriptSig, transactionInput.scriptSig)
    }

    @Test
    fun `Given an empty sourceTxId, When a TransactionInput is created, Then it should throw IllegalArgumentException`() {
        // Given
        val sourceTxId = ""
        val sourceIndex = 0
        val scriptSig = "3045022100"

        // Then
        assertThrows(IllegalArgumentException::class.java) {
            // When
            TransactionInput(sourceTxId, sourceIndex, scriptSig)
        }
    }

    @Test
    fun `Given an invalid scriptSig, When a TransactionInput is created, Then it should throw IllegalArgumentException`() {
        // Given
        val sourceTxId = "def456"
        val sourceIndex = 1
        val scriptSig = "XYZ"

        // Then
        assertThrows(IllegalArgumentException::class.java) {
            // When
            TransactionInput(sourceTxId, sourceIndex, scriptSig)
        }
    }

    @Test
    fun `Given a boundary value sourceIndex, When a TransactionInput is created, Then it should store correct values`() {
        // Given
        val sourceTxId = "ghi789"
        val sourceIndex = 2147483647
        val scriptSig = "3045022100"

        // When
        val transactionInput = TransactionInput(sourceTxId, sourceIndex, scriptSig)

        // Then
        assertEquals(sourceTxId, transactionInput.sourceTxId)
        assertEquals(sourceIndex, transactionInput.sourceIndex)
        assertEquals(scriptSig, transactionInput.scriptSig)
    }

    @Test
    fun `Given a negative sourceIndex, When a TransactionInput is created, Then it should throw IllegalArgumentException`() {
        // Given
        val sourceTxId = "jkl012"
        val sourceIndex = -1
        val scriptSig = "3045022100"

        // Then
        assertThrows(IllegalArgumentException::class.java) {
            // When
            TransactionInput(sourceTxId, sourceIndex, scriptSig)
        }
    }

    @Test
    fun `Given a maximum length scriptSig, When a TransactionInput is created, Then it should store correct values`() {
        // Given
        val sourceTxId = "mno345"
        val sourceIndex = 2
        val scriptSig = "a".repeat(256)  // Assuming 256 is the max length

        // When
        val transactionInput = TransactionInput(sourceTxId, sourceIndex, scriptSig)

        // Then
        assertEquals(sourceTxId, transactionInput.sourceTxId)
        assertEquals(sourceIndex, transactionInput.sourceIndex)
        assertEquals(scriptSig, transactionInput.scriptSig)
    }
}

package com.example.bitcoin.tests

import com.example.bitcoin.TransactionInput
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class TransactionInputTest {

    @Test
    fun `Functional_ValidTransactionInputCreation`() {
        // Given
        val sourceTxId = "abc123"
        val sourceIndex = 0
        val scriptSig = "3045022100f3"

        // When
        val transactionInput = TransactionInput(sourceTxId, sourceIndex, scriptSig)

        // Then
        assertEquals(sourceTxId, transactionInput.sourceTxId)
        assertEquals(sourceIndex, transactionInput.sourceIndex)
        assertEquals(scriptSig, transactionInput.scriptSig)
    }

    @Test
    fun `Functional_InvalidTransactionIDFormat`() {
        // Given
        val sourceTxId = "123"
        val sourceIndex = 0
        val scriptSig = "3045022100f3"

        // When & Then
        val exception = assertThrows(IllegalArgumentException::class.java) {
            TransactionInput(sourceTxId, sourceIndex, scriptSig)
        }
        assertEquals("Invalid transaction ID format", exception.message)
    }

    @Test
    fun `Functional_NegativeSourceIndex`() {
        // Given
        val sourceTxId = "abc123"
        val sourceIndex = -1
        val scriptSig = "3045022100f3"

        // When & Then
        val exception = assertThrows(IllegalArgumentException::class.java) {
            TransactionInput(sourceTxId, sourceIndex, scriptSig)
        }
        assertEquals("Invalid source index", exception.message)
    }

    @Test
    fun `Functional_EmptyScriptSignature`() {
        // Given
        val sourceTxId = "abc123"
        val sourceIndex = 0
        val scriptSig = ""

        // When & Then
        val exception = assertThrows(IllegalArgumentException::class.java) {
            TransactionInput(sourceTxId, sourceIndex, scriptSig)
        }
        assertEquals("Missing script signature", exception.message)
    }

    @Test
    fun `Boundary_BoundaryValueSourceIndex`() {
        // Given
        val sourceTxId = "abc123"
        val sourceIndex = 4294967295
        val scriptSig = "3045022100f3"

        // When
        val transactionInput = TransactionInput(sourceTxId, sourceIndex, scriptSig)

        // Then
        assertEquals(sourceTxId, transactionInput.sourceTxId)
        assertEquals(sourceIndex, transactionInput.sourceIndex)
        assertEquals(scriptSig, transactionInput.scriptSig)
    }
}


package com.example.bitcoin.tests

import com.example.bitcoin.TransactionInput
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class TransactionInputTest {

    @Test
    fun `Functional_ValidTransactionInputCreation`() {
        // Given
        val sourceTxId = "abc123"
        val sourceIndex = 0
        val scriptSig = "3045022100..."

        // When
        val transactionInput = TransactionInput(sourceTxId, sourceIndex, scriptSig)

        // Then
        assertEquals(sourceTxId, transactionInput.sourceTxId)
        assertEquals(sourceIndex, transactionInput.sourceIndex)
        assertEquals(scriptSig, transactionInput.scriptSig)
    }

    @Test
    fun `Functional_InvalidSourceTxIdEmpty`() {
        // Given
        val sourceTxId = ""
        val sourceIndex = 0
        val scriptSig = "3045022100..."

        // When & Then
        assertThrows(IllegalArgumentException::class.java) {
            TransactionInput(sourceTxId, sourceIndex, scriptSig)
        }
    }

    @Test
    fun `Functional_InvalidSourceTxIdFormat`() {
        // Given
        val sourceTxId = "invalidFormat123"
        val sourceIndex = 0
        val scriptSig = "3045022100..."

        // When & Then
        assertThrows(IllegalArgumentException::class.java) {
            TransactionInput(sourceTxId, sourceIndex, scriptSig)
        }
    }

    @Test
    fun `Functional_InvalidSourceIndexNegative`() {
        // Given
        val sourceTxId = "abc123"
        val sourceIndex = -1
        val scriptSig = "3045022100..."

        // When & Then
        assertThrows(IllegalArgumentException::class.java) {
            TransactionInput(sourceTxId, sourceIndex, scriptSig)
        }
    }

    @Test
    fun `Functional_ValidSourceIndexMaxValue`() {
        // Given
        val sourceTxId = "abc123"
        val sourceIndex = Int.MAX_VALUE
        val scriptSig = "3045022100..."

        // When
        val transactionInput = TransactionInput(sourceTxId, sourceIndex, scriptSig)

        // Then
        assertEquals(sourceTxId, transactionInput.sourceTxId)
        assertEquals(sourceIndex, transactionInput.sourceIndex)
        assertEquals(scriptSig, transactionInput.scriptSig)
    }

    @Test
    fun `Functional_InvalidScriptSigEmpty`() {
        // Given
        val sourceTxId = "abc123"
        val sourceIndex = 0
        val scriptSig = ""

        // When & Then
        assertThrows(IllegalArgumentException::class.java) {
            TransactionInput(sourceTxId, sourceIndex, scriptSig)
        }
    }

    @Test
    fun `Functional_ValidScriptSigFormat`() {
        // Given
        val sourceTxId = "abc123"
        val sourceIndex = 0
        val scriptSig = "3045022100..."

        // When
        val transactionInput = TransactionInput(sourceTxId, sourceIndex, scriptSig)

        // Then
        assertEquals(sourceTxId, transactionInput.sourceTxId)
        assertEquals(sourceIndex, transactionInput.sourceIndex)
        assertEquals(scriptSig, transactionInput.scriptSig)
    }

}