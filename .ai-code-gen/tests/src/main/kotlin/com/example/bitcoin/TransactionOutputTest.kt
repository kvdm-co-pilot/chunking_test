package com.example.bitcoin.tests

import com.example.bitcoin.TransactionOutput
import com.example.bitcoin.Address
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TransactionOutputTest {

    private lateinit var transactionOutput: TransactionOutput
    private lateinit var address: Address

    @BeforeEach
    fun setup() {
        address = Address("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa")
    }

    @Test
    fun `Functional_CreateValidTransactionOutput`() {
        // Given
        val amountSats = 5000000000L
        val scriptPubKey = "76a91488ac"

        // When
        transactionOutput = TransactionOutput(address, amountSats, scriptPubKey)

        // Then
        assertEquals(address, transactionOutput.address)
        assertEquals(amountSats, transactionOutput.amountSats)
        assertEquals(scriptPubKey, transactionOutput.scriptPubKey)
    }

    @Test
    fun `Functional_TransactionOutputWithMinimumAmount`() {
        // Given
        val address = Address("1BoatSLRHtKNngkdXEeobR76b53LETtpyT")
        val amountSats = 1L
        val scriptPubKey = "76a91488ac"

        // When
        transactionOutput = TransactionOutput(address, amountSats, scriptPubKey)

        // Then
        assertEquals(address, transactionOutput.address)
        assertEquals(amountSats, transactionOutput.amountSats)
        assertEquals(scriptPubKey, transactionOutput.scriptPubKey)
    }

    @Test
    fun `Functional_TransactionOutputWithMaximumAmount`() {
        // Given
        val address = Address("1dice8EMZmqKvrGE4Qc9bUFf9PX3xaYDp")
        val amountSats = 2100000000000000L
        val scriptPubKey = "76a91488ac"

        // When
        transactionOutput = TransactionOutput(address, amountSats, scriptPubKey)

        // Then
        assertEquals(address, transactionOutput.address)
        assertEquals(amountSats, transactionOutput.amountSats)
        assertEquals(scriptPubKey, transactionOutput.scriptPubKey)
    }

    @Test
    fun `Functional_TransactionOutputWithInvalidAddress`() {
        // Given
        val invalidAddress = Address("invalidBitcoinAddress123")
        val amountSats = 1000L
        val scriptPubKey = "76a91488ac"

        // Expect an exception
        assertThrows<IllegalArgumentException> {
            TransactionOutput(invalidAddress, amountSats, scriptPubKey)
        }
    }

    @Test
    fun `Functional_TransactionOutputWithNegativeAmount`() {
        // Given
        val address = Address("1dice8EMZmqKvrGE4Qc9bUFf9PX3xaYDp")
        val amountSats = -5000L
        val scriptPubKey = "76a91488ac"

        // Expect an exception
        assertThrows<IllegalArgumentException> {
            TransactionOutput(address, amountSats, scriptPubKey)
        }
    }

    @Test
    fun `Functional_TransactionOutputWithZeroAmount`() {
        // Given
        val address = Address("1dice8EMZmqKvrGE4Qc9bUFf9PX3xaYDp")
        val amountSats = 0L
        val scriptPubKey = "76a91488ac"

        // Expect an exception
        assertThrows<IllegalArgumentException> {
            TransactionOutput(address, amountSats, scriptPubKey)
        }
    }

    @Test
    fun `Functional_TransactionOutputWithInvalidScriptPubKey`() {
        // Given
        val address = Address("1dice8EMZmqKvrGE4Qc9bUFf9PX3xaYDp")
        val amountSats = 1000L
        val invalidScriptPubKey = "invalidScript"

        // Expect an exception
        assertThrows<IllegalArgumentException> {
            TransactionOutput(address, amountSats, invalidScriptPubKey)
        }
    }
}