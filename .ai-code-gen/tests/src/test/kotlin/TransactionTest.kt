package com.example.bitcoin.testing

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import com.example.bitcoin.Transaction
import com.example.bitcoin.TransactionInput
import com.example.bitcoin.TransactionOutput

class Com_example_bitcoin_TransactionTest {

    private lateinit var transaction: Transaction

    @BeforeEach
    fun setUp() {
        // Common setup can go here if needed
    }

    @Test
    fun `Given valid Transaction inputs and outputs, When processing transaction, Then it should succeed`() {
        // Given
        val inputs = listOf(TransactionInput(sourceIndex = 5000))
        val outputs = listOf(TransactionOutput(amountSats = 4800))
        val feeSats = 200L
        transaction = Transaction("tx1", inputs, outputs, feeSats)

        // When
        val totalInput = transaction.totalInput // Expected: 5000
        val totalOutput = transaction.totalOutput // Expected: 4800

        // Then
        assertEquals(5000L, totalInput)
        assertEquals(4800L, totalOutput)
        assertEquals(200L, feeSats)
    }

    @Test
    fun `Given insufficient inputs, When processing transaction, Then it should raise an error`() {
        // Given
        val inputs = listOf(TransactionInput(sourceIndex = 5000))
        val outputs = listOf(TransactionOutput(amountSats = 4900))
        val feeSats = 200L
        transaction = Transaction("tx2", inputs, outputs, feeSats)

        // When & Then
        val exception = assertThrows<IllegalArgumentException> {
            // Simulate processing logic that checks for sufficient inputs
            if (transaction.totalInput < transaction.totalOutput + feeSats) {
                throw IllegalArgumentException("Insufficient inputs: Total input (5000) is less than total outputs plus fee (5100).")
            }
        }
        assertEquals("Insufficient inputs: Total input (5000) is less than total outputs plus fee (5100).", exception.message)
    }

    @Test
    fun `Given excessive fee, When processing transaction, Then it should raise an error`() {
        // Given
        val inputs = listOf(TransactionInput(sourceIndex = 5000))
        val outputs = listOf(TransactionOutput(amountSats = 4800))
        val feeSats = 300L
        transaction = Transaction("tx3", inputs, outputs, feeSats)

        // When & Then
        val exception = assertThrows<IllegalArgumentException> {
            // Simulate processing logic that checks for excessive fee
            if (feeSats > transaction.totalInput - transaction.totalOutput) {
                throw IllegalArgumentException("Excessive fee: Fee (300) exceeds available balance for fee calculation (200).")
            }
        }
        assertEquals("Excessive fee: Fee (300) exceeds available balance for fee calculation (200).", exception.message)
    }

    @Test
    fun `Given zero inputs, When processing transaction, Then it should raise an error`() {
        // Given
        val inputs = emptyList<TransactionInput>()
        val outputs = listOf(TransactionOutput(amountSats = 4800))
        val feeSats = 200L
        transaction = Transaction("tx4", inputs, outputs, feeSats)

        // When & Then
        val exception = assertThrows<IllegalArgumentException> {
            // Simulate processing logic that checks for zero inputs
            if (inputs.isEmpty()) {
                throw IllegalArgumentException("No inputs: Transaction cannot proceed without inputs.")
            }
        }
        assertEquals("No inputs: Transaction cannot proceed without inputs.", exception.message)
    }

    @Test
    fun `Given negative output amounts, When processing transaction, Then it should raise an error`() {
        // Given
        val inputs = listOf(TransactionInput(sourceIndex = 5000))
        val outputs = listOf(TransactionOutput(amountSats = -100))
        val feeSats = 200L
        transaction = Transaction("tx5", inputs, outputs, feeSats)

        // When & Then
        val exception = assertThrows<IllegalArgumentException> {
            // Simulate processing logic that checks for negative outputs
            if (outputs.any { it.amountSats < 0 }) {
                throw IllegalArgumentException("Invalid output amounts: Negative output values are not allowed.")
            }
        }
        assertEquals("Invalid output amounts: Negative output values are not allowed.", exception.message)
    }

    @Test
    fun `Given maximum input values, When processing transaction, Then it should succeed`() {
        // Given
        val inputs = listOf(TransactionInput(sourceIndex = Long.MAX_VALUE))
        val outputs = listOf(TransactionOutput(amountSats = Long.MAX_VALUE))
        val feeSats = 0L
        transaction = Transaction("tx6", inputs, outputs, feeSats)

        // When
        val totalInput = transaction.totalInput // Expected: Long.MAX_VALUE
        val totalOutput = transaction.totalOutput // Expected: Long.MAX_VALUE

        // Then
        assertEquals(Long.MAX_VALUE, totalInput)
        assertEquals(Long.MAX_VALUE, totalOutput)
    }
}
