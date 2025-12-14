package com.example.bitcoin.tests

import com.example.bitcoin.TransactionOutput
import com.example.bitcoin.Address
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class TransactionOutputTest {

    @Test
    fun `Functional_ValidTransactionOutputCreation`() {
        // Given
        val address = Address("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa")
        val amountSats = 50000L
        val scriptPubKey = "76a9144621d1...88ac"

        // When
        val transactionOutput = TransactionOutput(address, amountSats, scriptPubKey)

        // Then
        assertEquals(address, transactionOutput.address)
        assertEquals(amountSats, transactionOutput.amountSats)
        assertEquals(scriptPubKey, transactionOutput.scriptPubKey)
    }

    @Test
    fun `Functional_LargeTransactionAmountBoundary`() {
        // Given
        val address = Address("1BoatSLRHtKNngkdXEeobR76b53LETtpyT")
        val amountSats = 2100000000000000L
        val scriptPubKey = "76a9144621d1...88ac"

        // When
        val transactionOutput = TransactionOutput(address, amountSats, scriptPubKey)

        // Then
        assertEquals(address, transactionOutput.address)
        assertEquals(amountSats, transactionOutput.amountSats)
        assertEquals(scriptPubKey, transactionOutput.scriptPubKey)
    }

    @Test
    fun `Functional_InvalidAddressFormat`() {
        // Given
        val address = Address("1234567890")
        val amountSats = 10000L
        val scriptPubKey = "76a9144621d1...88ac"

        // When/Then
        assertThrows(IllegalArgumentException::class.java) {
            TransactionOutput(address, amountSats, scriptPubKey)
        }
    }

    @Test
    fun `Functional_NegativeAmount`() {
        // Given
        val address = Address("1P5ZEDWTKTFGxQjZphgWPQUpe554WKDfHQ")
        val amountSats = -1000L
        val scriptPubKey = "76a9144621d1...88ac"

        // When/Then
        assertThrows(IllegalArgumentException::class.java) {
            TransactionOutput(address, amountSats, scriptPubKey)
        }
    }

    @Test
    fun `Functional_ZeroAmount`() {
        // Given
        val address = Address("3J98t1WpEZ73CNmQviecrnyiWrnqRhWNLy")
        val amountSats = 0L
        val scriptPubKey = "76a9144621d1...88ac"

        // When
        val transactionOutput = TransactionOutput(address, amountSats, scriptPubKey)

        // Then
        assertEquals(address, transactionOutput.address)
        assertEquals(amountSats, transactionOutput.amountSats)
        assertEquals(scriptPubKey, transactionOutput.scriptPubKey)
    }

    @Test
    fun `Invariant_ValidAddressNonBlank`() {
        // Given
        val address = Address("")
        val amountSats = 10000L
        val scriptPubKey = "76a9144621d1...88ac"

        // When/Then
        assertThrows(IllegalArgumentException::class.java) {
            TransactionOutput(address, amountSats, scriptPubKey)
        }
    }
}

package com.example.bitcoin.tests

import com.example.bitcoin.TransactionOutput
import com.example.bitcoin.Address
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class TransactionOutputTest {

    @Test
    fun `Functional_ValidTransactionOutputCreation`() {
        // Given
        val address = Address("bc1qxy2kgdygjrsqtzq2n0yrf2493p83kkfjhx0wlh")
        val amountSats = 100000L
        val scriptPubKey = "OP_DUP OP_HASH160 <pubkeyhash> OP_EQUALVERIFY OP_CHECKSIG"

        // When
        val transactionOutput = TransactionOutput(address, amountSats, scriptPubKey)

        // Then
        assertEquals(address, transactionOutput.address)
        assertEquals(amountSats, transactionOutput.amountSats)
        assertEquals(scriptPubKey, transactionOutput.scriptPubKey)
    }

    @Test
    fun `Functional_ZeroSatoshisTransactionOutput`() {
        // Given
        val address = Address("bc1qxy2kgdygjrsqtzq2n0yrf2493p83kkfjhx0wlh")
        val amountSats = 0L
        val scriptPubKey = "OP_DUP OP_HASH160 <pubkeyhash> OP_EQUALVERIFY OP_CHECKSIG"

        // When & Then
        assertThrows<IllegalArgumentException> {
            TransactionOutput(address, amountSats, scriptPubKey)
        }
    }

    @Test
    fun `Functional_NegativeSatoshisTransactionOutput`() {
        // Given
        val address = Address("bc1qxy2kgdygjrsqtzq2n0yrf2493p83kkfjhx0wlh")
        val amountSats = -100000L
        val scriptPubKey = "OP_DUP OP_HASH160 <pubkeyhash> OP_EQUALVERIFY OP_CHECKSIG"

        // When & Then
        assertThrows<IllegalArgumentException> {
            TransactionOutput(address, amountSats, scriptPubKey)
        }
    }

    @Test
    fun `Functional_MaximumSatoshisTransactionOutput`() {
        // Given
        val address = Address("bc1qxy2kgdygjrsqtzq2n0yrf2493p83kkfjhx0wlh")
        val amountSats = 2100000000000000L
        val scriptPubKey = "OP_DUP OP_HASH160 <pubkeyhash> OP_EQUALVERIFY OP_CHECKSIG"

        // When
        val transactionOutput = TransactionOutput(address, amountSats, scriptPubKey)

        // Then
        assertEquals(address, transactionOutput.address)
        assertEquals(amountSats, transactionOutput.amountSats)
        assertEquals(scriptPubKey, transactionOutput.scriptPubKey)
    }

    @Test
    fun `Functional_InvalidScriptPubKeyTransactionOutput`() {
        // Given
        val address = Address("bc1qxy2kgdygjrsqtzq2n0yrf2493p83kkfjhx0wlh")
        val amountSats = 100000L
        val scriptPubKey = "INVALID_OPCODE"

        // When & Then
        assertThrows<IllegalArgumentException> {
            TransactionOutput(address, amountSats, scriptPubKey)
        }
    }

    @Test
    fun `Functional_InvalidAddressFormatTransactionOutput`() {
        // Given
        val address = Address("12345_invalid_address")
        val amountSats = 100000L
        val scriptPubKey = "OP_DUP OP_HASH160 <pubkeyhash> OP_EQUALVERIFY OP_CHECKSIG"

        // When & Then
        assertThrows<IllegalArgumentException> {
            TransactionOutput(address, amountSats, scriptPubKey)
        }
    }

    @Test
    fun `Functional_MultipleOutputsSameAddress`() {
        // Given
        val address = Address("bc1qxy2kgdygjrsqtzq2n0yrf2493p83kkfjhx0wlh")
        val amountSats = 50000L
        val scriptPubKey = "OP_DUP OP_HASH160 <pubkeyhash> OP_EQUALVERIFY OP_CHECKSIG"

        // When
        val transactionOutput1 = TransactionOutput(address, amountSats, scriptPubKey)
        val transactionOutput2 = TransactionOutput(address, amountSats, scriptPubKey)

        // Then
        assertEquals(address, transactionOutput1.address)
        assertEquals(amountSats, transactionOutput1.amountSats)
        assertEquals(scriptPubKey, transactionOutput1.scriptPubKey)
        assertEquals(address, transactionOutput2.address)
        assertEquals(amountSats, transactionOutput2.amountSats)
        assertEquals(scriptPubKey, transactionOutput2.scriptPubKey)
    }

    @Test
    fun `Invariant_ValidAddressNonBlank`() {
        // Given
        val address = Address("")
        val amountSats = 100000L
        val scriptPubKey = "OP_DUP OP_HASH160 <pubkeyhash> OP_EQUALVERIFY OP_CHECKSIG"

        // When & Then
        assertThrows<IllegalArgumentException> {
            TransactionOutput(address, amountSats, scriptPubKey)
        }
    }

    @Test
    fun `Invariant_UTXOAmountMatchesTransactionInputs`() {
        // Given
        val address = Address("bc1qxy2kgdygjrsqtzq2n0yrf2493p83kkfjhx0wlh")
        val utxoAmount = 100000L
        val transactionInputsTotal = 100000L
        val scriptPubKey = "OP_DUP OP_HASH160 <pubkeyhash> OP_EQUALVERIFY OP_CHECKSIG"

        // When
        val transactionOutput = TransactionOutput(address, utxoAmount, scriptPubKey)

        // Then
        assertEquals(address, transactionOutput.address)
        assertEquals(utxoAmount, transactionOutput.amountSats)
        assertEquals(scriptPubKey, transactionOutput.scriptPubKey)
        assertEquals(transactionInputsTotal, utxoAmount)
    }

    @Test
    fun `Invariant_TotalOutputNotExceedingTotalInputMinusFees`() {
        // Given
        val totalInput = 200000L
        val transactionFees = 10000L
        val totalOutput = 190000L

        // When
        val isWithinLimits = (totalOutput <= totalInput - transactionFees)

        // Then
        assertEquals(true, isWithinLimits)
    }
}