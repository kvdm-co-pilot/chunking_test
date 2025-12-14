package com.example.bitcoin.tests

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import com.example.bitcoin.Transaction
import com.example.bitcoin.TransactionInput
import com.example.bitcoin.TransactionOutput
import com.example.bitcoin.Address

class TransactionTest {

    @Test
    fun `Given valid inputs and outputs When processing transaction Then it should succeed`() {
        // Given
        val inputs = listOf(TransactionInput("source1", 1000), TransactionInput("source2", 2000), TransactionInput("source3", 500))
        val outputs = listOf(TransactionOutput(Address("1BitcoinAddressExample"), 3200, "76a91488ac"))
        val feeSats = 300L

        // When
        val transaction = Transaction("tx123", inputs, outputs, feeSats)

        // Then
        assertEquals(3500, transaction.totalInput)
        assertEquals(3200, transaction.totalOutput)
    }

    @Test
    fun `Given insufficient inputs When processing transaction Then it should be rejected`() {
        // Given
        val inputs = listOf(TransactionInput("source1", 1000), TransactionInput("source2", 500))
        val outputs = listOf(TransactionOutput(Address("1BitcoinAddressExample"), 1400, "76a91488ac"))
        val feeSats = 200L

        // When
        val exception = assertThrows<IllegalArgumentException> {
            Transaction("tx124", inputs, outputs, feeSats)
        }

        // Then
        assertEquals("Insufficient inputs for transaction", exception.message)
    }

    @Test
    fun `Given excessive fee When processing transaction Then it should be rejected`() {
        // Given
        val inputs = listOf(TransactionInput("source1", 5000), TransactionInput("source2", 3000))
        val outputs = listOf(TransactionOutput(Address("1BitcoinAddressExample"), 7000, "76a91488ac"))
        val feeSats = 2000L

        // When
        val exception = assertThrows<IllegalArgumentException> {
            Transaction("tx125", inputs, outputs, feeSats)
        }

        // Then
        assertEquals("Excessive fee for transaction", exception.message)
    }

    @Test
    fun `Given zero or negative inputs When processing transaction Then it should be rejected`() {
        // Given
        val inputs = listOf(TransactionInput("source1", 0), TransactionInput("source2", -500))
        val outputs = listOf(TransactionOutput(Address("1BitcoinAddressExample"), 500, "76a91488ac"))
        val feeSats = 50L

        // When
        val exception = assertThrows<IllegalArgumentException> {
            Transaction("tx126", inputs, outputs, feeSats)
        }

        // Then
        assertEquals("Invalid input values for transaction", exception.message)
    }

    @Test
    fun `Given zero or negative outputs When processing transaction Then it should be rejected`() {
        // Given
        val inputs = listOf(TransactionInput("source1", 1000), TransactionInput("source2", 500))
        val outputs = listOf(TransactionOutput(Address("1BitcoinAddressExample"), -200, "76a91488ac"), TransactionOutput(Address("1BitcoinAddressExample"), 0, "76a91488ac"))
        val feeSats = 50L

        // When
        val exception = assertThrows<IllegalArgumentException> {
            Transaction("tx127", inputs, outputs, feeSats)
        }

        // Then
        assertEquals("Invalid output values for transaction", exception.message)
    }

    @Test
    fun `Given minimum fee When processing transaction Then it should succeed`() {
        // Given
        val inputs = listOf(TransactionInput("source1", 1000), TransactionInput("source2", 500))
        val outputs = listOf(TransactionOutput(Address("1BitcoinAddressExample"), 1499, "76a91488ac"))
        val feeSats = 1L

        // When
        val transaction = Transaction("tx128", inputs, outputs, feeSats)

        // Then
        assertEquals(1500, transaction.totalInput)
        assertEquals(1499, transaction.totalOutput)
    }

    @Test
    fun `Given maximum inputs and outputs When processing transaction Then it should succeed`() {
        // Given
        val inputs = listOf(TransactionInput("source1", 1000), TransactionInput("source2", 2000), TransactionInput("source3", 500), TransactionInput("source4", 300), TransactionInput("source5", 700))
        val outputs = listOf(TransactionOutput(Address("1BitcoinAddressExample"), 1000, "76a91488ac"), TransactionOutput(Address("1BitcoinAddressExample"), 3000, "76a91488ac"), TransactionOutput(Address("1BitcoinAddressExample"), 500, "76a91488ac"))
        val feeSats = 200L

        // When
        val transaction = Transaction("tx129", inputs, outputs, feeSats)

        // Then
        assertEquals(4500, transaction.totalInput)
        assertEquals(4500, transaction.totalOutput + feeSats)
    }
}
