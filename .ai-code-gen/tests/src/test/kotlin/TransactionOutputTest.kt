package com.example.bitcoin.test

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import com.example.bitcoin.TransactionOutput
import com.example.bitcoin.Address

class TransactionOutputTest {

    private lateinit var transactionOutput: TransactionOutput
    private lateinit var address: Address

    @BeforeEach
    fun setUp() {
        // Initialize with valid data
        address = Address("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa")
    }

    @Test
    fun `Functional_ValidTransactionOutputCreation`() {
        transactionOutput = TransactionOutput(address, 5000000000L, "76a91488ac")
        assertEquals(address, transactionOutput.address)
        assertEquals(5000000000L, transactionOutput.amountSats)
        assertEquals("76a91488ac", transactionOutput.scriptPubKey)
    }

    @Test
    fun `Functional_TransactionWithZeroSatoshis`() {
        transactionOutput = TransactionOutput(address, 0L, "76a91488ac")
        assertEquals(0L, transactionOutput.amountSats)
        // Additional logic may flag this for review
    }

    @Test
    fun `Functional_TransactionWithNegativeSatoshis`() {
        val exception = assertThrows<IllegalArgumentException> {
            TransactionOutput(address, -1000L, "76a91488ac")
        }
        assertEquals("Invalid amount value", exception.message)
    }

    @Test
    fun `Functional_InvalidBitcoinAddress`() {
        val invalidAddress = Address("12345InvalidAddress")
        val exception = assertThrows<IllegalArgumentException> {
            TransactionOutput(invalidAddress, 500000L, "76a91488ac")
        }
        assertEquals("Invalid address format", exception.message)
    }

    @Test
    fun `Functional_MaximumSatoshisTransaction`() {
        transactionOutput = TransactionOutput(address, 2100000000000000L, "76a91488ac")
        assertEquals(2100000000000000L, transactionOutput.amountSats)
    }

    @Test
    fun `Functional_ScriptPubKeyValidation`() {
        val exception = assertThrows<IllegalArgumentException> {
            TransactionOutput(address, 500000L, "InvalidScript")
        }
        assertEquals("Invalid scriptPubKey format", exception.message)
    }
}