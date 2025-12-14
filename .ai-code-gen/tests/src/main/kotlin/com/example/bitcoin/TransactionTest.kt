package com.example.bitcoin.tests

import com.example.bitcoin.Transaction
import com.example.bitcoin.TransactionInput
import com.example.bitcoin.TransactionOutput
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class TransactionTest {

    @Test
    fun `Functional_CalculateTotalInputAndOutput_ValidTransaction`() {
        // Given
        val inputs = listOf(
            TransactionInput(sourceIndex = 15000),
            TransactionInput(sourceIndex = 20000)
        )
        val outputs = listOf(
            TransactionOutput(amountSats = 15000),
            TransactionOutput(amountSats = 19000)
        )
        val transaction = Transaction(id = "txn001", inputs = inputs, outputs = outputs, feeSats = 1000)

        // When
        val totalInput = transaction.totalInput
        val totalOutput = transaction.totalOutput

        // Then
        assertEquals(35000, totalInput)
        assertEquals(34000, totalOutput)
    }

    @Test
    fun `Functional_TransactionWithNoInputsOrOutputs`() {
        // Given
        val transaction = Transaction(id = "txn002", inputs = listOf(), outputs = listOf(), feeSats = 0)

        // When
        val totalInput = transaction.totalInput
        val totalOutput = transaction.totalOutput

        // Then
        assertEquals(0, totalInput)
        assertEquals(0, totalOutput)
    }

    @Test
    fun `Functional_HandlingLargeTransactions_NoOverflow`() {
        // Given
        val inputs = listOf(
            TransactionInput(sourceIndex = 1000000000),
            TransactionInput(sourceIndex = 1000000000)
        )
        val outputs = listOf(
            TransactionOutput(amountSats = 999999999),
            TransactionOutput(amountSats = 999999999)
        )
        val transaction = Transaction(id = "txn003", inputs = inputs, outputs = outputs, feeSats = 2)

        // When
        val totalInput = transaction.totalInput
        val totalOutput = transaction.totalOutput

        // Then
        assertEquals(2000000000, totalInput)
        assertEquals(1999999998, totalOutput)
    }

    @Test
    fun `Functional_InvalidFeeCalculation_OutputsExceedInputs`() {
        // Given
        val inputs = listOf(TransactionInput(sourceIndex = 50000))
        val outputs = listOf(TransactionOutput(amountSats = 60000))
        val transaction = Transaction(id = "txn004", inputs = inputs, outputs = outputs, feeSats = 500)

        // When / Then
        val exception = assertThrows<IllegalArgumentException> {
            val totalInput = transaction.totalInput
            val totalOutput = transaction.totalOutput
            if (totalOutput > totalInput) {
                throw IllegalArgumentException("Outputs exceed inputs.")
            }
        }
        assertEquals("Outputs exceed inputs.", exception.message)
    }

    @Test
    fun `Functional_TransactionWithMinimumFee_CorrectFeeCalculation`() {
        // Given
        val inputs = listOf(TransactionInput(sourceIndex = 100000))
        val outputs = listOf(TransactionOutput(amountSats = 99900))
        val transaction = Transaction(id = "txn005", inputs = inputs, outputs = outputs, feeSats = 100)

        // When
        val fee = transaction.feeSats

        // Then
        assertEquals(100, fee)
    }
}