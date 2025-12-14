package com.example.bitcoin.test

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import com.example.bitcoin.TransactionInput

class TransactionInputTest {
    private lateinit var transactionInput: TransactionInput

    @BeforeEach
    fun setUp() {
        transactionInput = TransactionInput("abc123", 0, "3045022100abc...")
    }

    @Test
    fun `Given a TransactionInput When checking the sourceTxId Then it should be valid`() {
        assertEquals("abc123", transactionInput.sourceTxId)
    }

    @Test
    fun `Given a TransactionInput When checking the sourceIndex Then it should be valid`() {
        assertEquals(0, transactionInput.sourceIndex)
    }

    @Test
    fun `Given a TransactionInput When checking the scriptSig Then it should be valid`() {
        assertEquals("3045022100abc...", transactionInput.scriptSig)
    }

    @Test
    fun `Given a TransactionInput with a nonexistent sourceTxId When validating Then it should throw an exception`() {
        assertThrows(IllegalArgumentException::class.java) {
            TransactionInput("xyz999", 0, "3045022100xyz...")
        }
    }

    @Test
    fun `Given a TransactionInput with an invalid scriptSig When validating Then it should throw an exception`() {
        assertThrows(IllegalArgumentException::class.java) {
            TransactionInput("abc123", 0, "invalidSig")
        }
    }

    @Test
    fun `Given a TransactionInput with maximum sourceIndex When validating Then it should be valid`() {
        val transactionInputMaxIndex = TransactionInput("abc123", 4294967295, "3045022100abc...")
        assertEquals(4294967295, transactionInputMaxIndex.sourceIndex)
    }

    @Test
    fun `Given a TransactionInput with null scriptSig When validating Then it should throw an exception`() {
        assertThrows(IllegalArgumentException::class.java) {
            TransactionInput("abc123", 0, null)
        }
    }
}
