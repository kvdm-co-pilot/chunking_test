package com.example.bitcoin.testing

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import com.example.bitcoin.TransactionOutput
import com.example.bitcoin.Address

class Com_example_bitcoin_TransactionOutputTest {

    private lateinit var transactionOutput: TransactionOutput

    @BeforeEach
    fun setUp() {
        // Setup is done per test case to ensure independence
    }

    @Test
    fun `Functional_CreateTransactionOutputWithValidData`() {
        // Given
        val address = Address("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa")
        val amountSats = 1000L
        val scriptPubKey = "76a9140b60b8768c0c3e5e894271a0d7f6e2c7e1d9c8c788ac"

        // When
        transactionOutput = TransactionOutput(address, amountSats, scriptPubKey)

        // Then
        assertEquals(address, transactionOutput.address)
        assertEquals(amountSats, transactionOutput.amountSats)
        assertEquals(scriptPubKey, transactionOutput.scriptPubKey)
    }

    @Test
    fun `Functional_FailToCreateTransactionOutputWithInvalidAddress`() {
        // Given
        val invalidAddress = Address("abcdefghijklmnopqrstuvwxyz")
        val amountSats = 1000L
        val scriptPubKey = "76a9140b60b8768c0c3e5e894271a0d7f6e2c7e1d9c8c788ac"

        // When & Then
        val exception = assertThrows<IllegalArgumentException> {
            TransactionOutput(invalidAddress, amountSats, scriptPubKey)
        }
        assertEquals("Invalid address format", exception.message)
    }

    @Test
    fun `Functional_FailToCreateTransactionOutputWithNegativeSatoshis`() {
        // Given
        val address = Address("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa")
        val negativeAmountSats = -500L
        val scriptPubKey = "76a9140b60b8768c0c3e5e894271a0d7f6e2c7e1d9c8c788ac"

        // When & Then
        val exception = assertThrows<IllegalArgumentException> {
            TransactionOutput(address, negativeAmountSats, scriptPubKey)
        }
        assertEquals("Invalid amount", exception.message)
    }

    @Test
    fun `Functional_SuccessfullyHandleMaximumSatoshiValue`() {
        // Given
        val address = Address("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa")
        val maxAmountSats = 21_000_000_000_000_000L
        val scriptPubKey = "76a9140b60b8768c0c3e5e894271a0d7f6e2c7e1d9c8c788ac"

        // When
        transactionOutput = TransactionOutput(address, maxAmountSats, scriptPubKey)

        // Then
        assertEquals(maxAmountSats, transactionOutput.amountSats)
    }

    @Test
    fun `Functional_HandleScriptPubKeyErrors`() {
        // Given
        val address = Address("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa")
        val amountSats = 1000L
        val invalidScriptPubKey = "INVALID_SCRIPT"

        // When & Then
        val exception = assertThrows<IllegalArgumentException> {
            TransactionOutput(address, amountSats, invalidScriptPubKey)
        }
        assertEquals("Invalid scriptPubKey", exception.message)
    }

    @Test
    fun `Functional_HandleZeroSatoshiTransaction`() {
        // Given
        val address = Address("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa")
        val zeroAmountSats = 0L
        val scriptPubKey = "76a9140b60b8768c0c3e5e894271a0d7f6e2c7e1d9c8c788ac"

        // When
        transactionOutput = TransactionOutput(address, zeroAmountSats, scriptPubKey)

        // Then
        assertEquals(zeroAmountSats, transactionOutput.amountSats)
    }

    // Additional tests can follow similar structure
}