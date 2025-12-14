package com.example.bitcoin.tests

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import com.example.bitcoin.TransactionOutput
import com.example.bitcoin.Address

class TransactionOutputTest {

    @Test
    fun `Given valid transaction output When created Then fields should match the input values`() {
        // Given
        val expectedAddress = Address("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa")
        val expectedAmountSats = 10000L
        val expectedScriptPubKey = "76a91488ac"

        // When
        val transactionOutput = TransactionOutput(
            address = expectedAddress,
            amountSats = expectedAmountSats,
            scriptPubKey = expectedScriptPubKey
        )

        // Then
        assertEquals(expectedAddress, transactionOutput.address)
        assertEquals(expectedAmountSats, transactionOutput.amountSats)
        assertEquals(expectedScriptPubKey, transactionOutput.scriptPubKey)
    }

    @Test
    fun `Given transaction output with zero amount When created Then exception should be thrown`() {
        // Given
        val address = Address("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa")

        // When & Then
        val exception = assertThrows(IllegalArgumentException::class.java) {
            TransactionOutput(
                address = address,
                amountSats = 0,
                scriptPubKey = "76a91488ac"
            )
        }
        assertEquals("Amount must be greater than zero", exception.message)
    }

    @Test
    fun `Given transaction output with negative amount When created Then exception should be thrown`() {
        // Given
        val address = Address("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa")

        // When & Then
        val exception = assertThrows(IllegalArgumentException::class.java) {
            TransactionOutput(
                address = address,
                amountSats = -1000,
                scriptPubKey = "76a91488ac"
            )
        }
        assertEquals("Amount must be positive", exception.message)
    }

    @Test
    fun `Given transaction output with invalid address When created Then exception should be thrown`() {
        // Given
        val invalidAddress = Address("invalid_address")

        // When & Then
        val exception = assertThrows(IllegalArgumentException::class.java) {
            TransactionOutput(
                address = invalidAddress,
                amountSats = 10000,
                scriptPubKey = "76a91488ac"
            )
        }
        assertEquals("Invalid address format", exception.message)
    }

    @Test
    fun `Given transaction output with max boundary amount When created Then it should be stored successfully`() {
        // Given
        val address = Address("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa")
        val maxAmountSats = 2100000000000000L
        val scriptPubKey = "76a91488ac"

        // When
        val transactionOutput = TransactionOutput(
            address = address,
            amountSats = maxAmountSats,
            scriptPubKey = scriptPubKey
        )

        // Then
        assertEquals(address, transactionOutput.address)
        assertEquals(maxAmountSats, transactionOutput.amountSats)
        assertEquals(scriptPubKey, transactionOutput.scriptPubKey)
    }

    @Test
    fun `Given transaction output with blank address When created Then exception should be thrown`() {
        // Given
        val blankAddress = Address("")

        // When & Then
        val exception = assertThrows(IllegalArgumentException::class.java) {
            TransactionOutput(
                address = blankAddress,
                amountSats = 10000,
                scriptPubKey = "76a91488ac"
            )
        }
        assertEquals("Address must not be blank", exception.message)
    }

    @Test
    fun `Given transaction output with invalid scriptPubKey When created Then exception should be thrown`() {
        // Given
        val address = Address("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa")

        // When & Then
        val exception = assertThrows(IllegalArgumentException::class.java) {
            TransactionOutput(
                address = address,
                amountSats = 10000,
                scriptPubKey = "invalid_script"
            )
        }
        assertEquals("Invalid scriptPubKey format", exception.message)
    }
}
