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
        // No initialization required for test cases that do not need setup
    }

    @Test
    fun `Given valid parameters When creating TransactionInput Then it should be created successfully`() {
        val sourceTxId = "abc123"
        val sourceIndex = 0
        val scriptSig = "3045022100"

        transactionInput = TransactionInput(sourceTxId, sourceIndex, scriptSig)

        assertEquals(sourceTxId, transactionInput.sourceTxId)
        assertEquals(sourceIndex, transactionInput.sourceIndex)
        assertEquals(scriptSig, transactionInput.scriptSig)
    }

    @Test
    fun `Given invalid transaction ID When creating TransactionInput Then it should throw an error`() {
        val invalidSourceTxId = "123"
        val sourceIndex = 0
        val scriptSig = "3045022100"

        assertThrows(IllegalArgumentException::class.java) {
            TransactionInput(invalidSourceTxId, sourceIndex, scriptSig)
        }
    }

    @Test
    fun `Given invalid source index When creating TransactionInput Then it should throw an error`() {
        val sourceTxId = "abc123"
        val invalidSourceIndex = -1
        val scriptSig = "3045022100"

        assertThrows(IllegalArgumentException::class.java) {
            TransactionInput(sourceTxId, invalidSourceIndex, scriptSig)
        }
    }

    @Test
    fun `Given empty script signature When creating TransactionInput Then it should throw an error`() {
        val sourceTxId = "abc123"
        val sourceIndex = 0
        val emptyScriptSig = ""

        assertThrows(IllegalArgumentException::class.java) {
            TransactionInput(sourceTxId, sourceIndex, emptyScriptSig)
        }
    }

    @Test
    fun `Given maximum source index When creating TransactionInput Then it should be created successfully`() {
        val sourceTxId = "abc123"
        val maxSourceIndex = 2147483647
        val scriptSig = "3045022100"

        transactionInput = TransactionInput(sourceTxId, maxSourceIndex, scriptSig)

        assertEquals(sourceTxId, transactionInput.sourceTxId)
        assertEquals(maxSourceIndex, transactionInput.sourceIndex)
        assertEquals(scriptSig, transactionInput.scriptSig)
    }

    @Test
    fun `Given script signature with special characters When creating TransactionInput Then it should be created successfully`() {
        val sourceTxId = "abc123"
        val sourceIndex = 0
        val specialCharScriptSig = "!@#$%^&*()"

        transactionInput = TransactionInput(sourceTxId, sourceIndex, specialCharScriptSig)

        assertEquals(sourceTxId, transactionInput.sourceTxId)
        assertEquals(sourceIndex, transactionInput.sourceIndex)
        assertEquals(specialCharScriptSig, transactionInput.scriptSig)
    }
}
