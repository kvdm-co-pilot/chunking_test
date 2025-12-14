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
        address = Address("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa")
    }

    @Test
    fun `Functional_SuccessfulTransactionOutputCreation`() {
        transactionOutput = TransactionOutput(address, 1000L, "OP_DUP OP_HASH160 [pubkeyHash] OP_EQUALVERIFY OP_CHECKSIG")
        assertEquals(address, transactionOutput.address)
        assertEquals(1000L, transactionOutput.amountSats)
        assertEquals("OP_DUP OP_HASH160 [pubkeyHash] OP_EQUALVERIFY OP_CHECKSIG", transactionOutput.scriptPubKey)
    }

    @Test
    fun `Functional_TransactionOutputWithZeroSatoshis`() {
        transactionOutput = TransactionOutput(address, 0L, "OP_DUP OP_HASH160 [pubkeyHash] OP_EQUALVERIFY OP_CHECKSIG")
        assertEquals(0L, transactionOutput.amountSats)
    }

    @Test
    fun `Functional_TransactionOutputWithNegativeSatoshis`() {
        assertThrows(IllegalArgumentException::class.java) {
            TransactionOutput(address, -1000L, "OP_DUP OP_HASH160 [pubkeyHash] OP_EQUALVERIFY OP_CHECKSIG")
        }
    }

    @Test
    fun `Functional_TransactionOutputWithMaximumSatoshis`() {
        transactionOutput = TransactionOutput(address, Long.MAX_VALUE, "OP_DUP OP_HASH160 [pubkeyHash] OP_EQUALVERIFY OP_CHECKSIG")
        assertEquals(Long.MAX_VALUE, transactionOutput.amountSats)
    }

    @Test
    fun `Functional_InvalidBitcoinAddressForTransactionOutput`() {
        val invalidAddress = Address("12345")
        assertThrows(IllegalArgumentException::class.java) {
            TransactionOutput(invalidAddress, 1000L, "OP_DUP OP_HASH160 [pubkeyHash] OP_EQUALVERIFY OP_CHECKSIG")
        }
    }

    @Test
    fun `Invariant_VerifyValidBitcoinAddressForTransactionOutput`() {
        val validAddresses = listOf(
            Address("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa"),
            Address("1BoatSLRHtKNngkdXEeobR76b53LETtpyT")
        )
        validAddresses.forEach {
            transactionOutput = TransactionOutput(it, 1000L, "OP_DUP OP_HASH160 [pubkeyHash] OP_EQUALVERIFY OP_CHECKSIG")
            assertEquals(it, transactionOutput.address)
        }
    }

    @Test
    fun `DomainBoundary_TransactionOutputWithDustLimitAmount`() {
        transactionOutput = TransactionOutput(address, 546L, "OP_DUP OP_HASH160 [pubkeyHash] OP_EQUALVERIFY OP_CHECKSIG")
        assertEquals(546L, transactionOutput.amountSats)
    }

    @Test
    fun `DomainBoundary_TransactionOutputWithMaximumBitcoinSupply`() {
        transactionOutput = TransactionOutput(address, 2100000000000000L, "OP_DUP OP_HASH160 [pubkeyHash] OP_EQUALVERIFY OP_CHECKSIG")
        assertEquals(2100000000000000L, transactionOutput.amountSats)
    }
}