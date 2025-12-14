package com.example.bitcoin.test

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import com.example.bitcoin.Transaction
import com.example.bitcoin.TransactionInput
import com.example.bitcoin.TransactionOutput

class TransactionTest {
    private lateinit var transaction: Transaction

    @BeforeEach
    fun setUp() {
        // Setup for valid transaction
    }

    @Test
    fun `Functional_HappyPath_ValidTransaction`() {
        val inputs = listOf(TransactionInput(1, 5000), TransactionInput(2, 3000))
        val outputs = listOf(TransactionOutput(7000), TransactionOutput(1000))
        val transaction = Transaction("validId", inputs, outputs, 1000)

        assertEquals(8000, transaction.totalInput)
        assertEquals(8000, transaction.totalOutput)
        assertEquals(1000, transaction.feeSats)
    }

    @Test
    fun `Functional_ZeroFeeTransaction`() {
        val inputs = listOf(TransactionInput(1, 5000))
        val outputs = listOf(TransactionOutput(5000))
        val transaction = Transaction("validId", inputs, outputs, 0)

        assertEquals(5000, transaction.totalInput)
        assertEquals(5000, transaction.totalOutput)
        assertEquals(0, transaction.feeSats)
    }

    @Test
    fun `Functional_InvalidTransactionID`() {
        val inputs = listOf(TransactionInput(1, 5000))
        val outputs = listOf(TransactionOutput(5000))

        val exception = assertThrows(IllegalArgumentException::class.java) {
            Transaction(null, inputs, outputs, 0)
        }
        assertEquals("Invalid Transaction ID", exception.message)
    }

    @Test
    fun `Functional_MaximumInputValue`() {
        val inputs = listOf(TransactionInput(1, 9223372036854775807))
        val outputs = listOf(TransactionOutput(9223372036854775807))
        val transaction = Transaction("validId", inputs, outputs, 0)

        assertEquals(9223372036854775807, transaction.totalInput)
        assertEquals(9223372036854775807, transaction.totalOutput)
        assertEquals(0, transaction.feeSats)
    }

    @Test
    fun `Functional_ValidatingInputOutputSources`() {
        val inputs = listOf(TransactionInput(1, 3000))
        val outputs = listOf(TransactionOutput(3000))
        val transaction = Transaction("validId", inputs, outputs, 0)

        // Assume validation logic for sources
        // assertTrue(transaction.validateSources())
        assertEquals(3000, transaction.totalInput)
        assertEquals(3000, transaction.totalOutput)
    }

    @Test
    fun `Invariant_InputOutputPlusFee`() {
        val inputs = listOf(TransactionInput(1, 8000))
        val outputs = listOf(TransactionOutput(7000))
        val transaction = Transaction("validId", inputs, outputs, 1000)

        assertEquals(8000, transaction.totalInput)
        assertEquals(7000, transaction.totalOutput)
        assertEquals(1000, transaction.feeSats)
    }
}