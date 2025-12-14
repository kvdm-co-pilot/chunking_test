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
        // No initialization needed for individual test cases
    }

    @Test
    fun `Given valid inputs When creating a TransactionInput Then it should be created successfully`() {
        transactionInput = TransactionInput("abc123", 0, "304502...")
        assertEquals("abc123", transactionInput.sourceTxId)
        assertEquals(0, transactionInput.sourceIndex)
        assertEquals("304502...", transactionInput.scriptSig)
    }

    @Test
    fun `Given invalid sourceTxId When creating a TransactionInput Then it should throw an error`() {
        val exception = assertThrows(IllegalArgumentException::class.java) {
            transactionInput = TransactionInput("xyz", 0, "304502...")
        }
        assertEquals("Invalid Transaction ID format", exception.message)
    }

    @Test
    fun `Given out-of-range sourceIndex When creating a TransactionInput Then it should throw an error`() {
        val exception = assertThrows(IllegalArgumentException::class.java) {
            transactionInput = TransactionInput("abc123", 10, "304502...")
        }
        assertEquals("Source index out of range", exception.message)
    }

    @Test
    fun `Given empty scriptSig When creating a TransactionInput Then it should throw an error`() {
        val exception = assertThrows(IllegalArgumentException::class.java) {
            transactionInput = TransactionInput("abc123", 0, "")
        }
        assertEquals("Script signature cannot be empty", exception.message)
    }

    @Test
    fun `Given maximum boundary values When creating a TransactionInput Then it should be created successfully`() {
        transactionInput = TransactionInput("abc123", 4294967295, "304502...")
        assertEquals("abc123", transactionInput.sourceTxId)
        assertEquals(4294967295, transactionInput.sourceIndex)
        assertEquals("304502...", transactionInput.scriptSig)
    }
}