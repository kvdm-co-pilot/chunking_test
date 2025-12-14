package com.example.bitcoin.tests

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import com.example.bitcoin.Wallet
import com.example.bitcoin.Transaction
import com.example.bitcoin.Address
import com.example.bitcoin.UTXO

class WalletTest {

    @Test
    fun `Given a wallet with a positive balance When retrieving balance Then the balance should match the expected positive value`() {
        // Given
        val wallet = Wallet(5000L)

        // When
        val balance = wallet.retrieveBalance()

        // Then
        assertEquals(5000L, balance)
    }

    @Test
    fun `Given a wallet with zero balance When retrieving balance Then the balance should be zero`() {
        // Given
        val wallet = Wallet(0L)

        // When
        val balance = wallet.retrieveBalance()

        // Then
        assertEquals(0L, balance)
    }

    @Test
    fun `Given a wallet after a transaction When retrieving balance Then the balance should reflect the transaction effect`() {
        // Given
        val wallet = Wallet(10000L)
        val transaction = Transaction(3000L)
        wallet.executeTransaction(transaction)

        // When
        val balance = wallet.retrieveBalance()

        // Then
        assertEquals(7000L, balance)
    }

    @Test
    fun `Given a wallet with maximum satoshi limit When retrieving balance Then the balance should match the maximum limit`() {
        // Given
        val wallet = Wallet(21_000_000_000_000L)

        // When
        val balance = wallet.retrieveBalance()

        // Then
        assertEquals(21_000_000_000_000L, balance)
    }

    @Test
    fun `Given a wallet with negative balance When retrieving balance Then an error should be thrown`() {
        // Given
        val wallet = Wallet(-5000L)

        // When & Then
        val exception = assertThrows<IllegalArgumentException> {
            wallet.retrieveBalance()
        }
        assertEquals("Invalid balance: negative value", exception.message)
    }

    @Test
    fun `Given a wallet with non-integer balance When retrieving balance Then an error should be thrown`() {
        // Given
        val wallet = Wallet(5000.5)

        // When & Then
        val exception = assertThrows<IllegalArgumentException> {
            wallet.retrieveBalance()
        }
        assertEquals("Invalid balance: non-integer value", exception.message)
    }

    @Test
    fun `Given a valid Bitcoin wallet address When verifying address Then the address should be confirmed as valid`() {
        // Given
        val address = Address("bc1abcxyz...")

        // When
        val isValid = address.verify()

        // Then
        assertEquals(true, isValid)
    }

    @Test
    fun `Given a transaction with input-output-fee structure When verifying Then the transaction should be confirmed as valid`() {
        // Given
        val transaction = Transaction(inputs = 10000L, outputs = 9950L, fee = 50L)

        // When
        val isValid = transaction.verify()

        // Then
        assertEquals(true, isValid)
    }

    @Test
    fun `Given a request to generate a private key When generating Then a secure private key should be produced`() {
        // Given
        val wallet = Wallet()

        // When
        val privateKey = wallet.generatePrivateKey()

        // Then
        assertEquals(true, wallet.isPrivateKeySecure(privateKey))
    }

    @Test
    fun `Given a UTXO marked as spent When verifying state transition Then the UTXO should be updated correctly`() {
        // Given
        val utxo = UTXO(3000L)
        utxo.markAsSpent()

        // When
        val isSpent = utxo.isSpent

        // Then
        assertEquals(true, isSpent)
    }

    @Test
    fun `Given a wallet after a transaction When verifying balance update Then the balance should be updated correctly`() {
        // Given
        val wallet = Wallet(10000L)
        val transaction = Transaction(2000L)
        wallet.executeTransaction(transaction)

        // When
        val balance = wallet.retrieveBalance()

        // Then
        assertEquals(8000L, balance)
    }

    @Test
    fun `Given valid Bitcoin address prefixes When verifying Then the addresses should be validated correctly`() {
        // Given
        val address1 = Address("bc1xyz...")
        val address2 = Address("1abc...")
        val address3 = Address("3def...")

        // When & Then
        assertEquals(true, address1.verify())
        assertEquals(true, address2.verify())
        assertEquals(true, address3.verify())
    }

    @Test
    fun `Given a transaction size When calculating minimum transaction fee Then the fee should be calculated correctly`() {
        // Given
        val transactionSize = 200

        // When
        val fee = Wallet.calculateTransactionFee(transactionSize)

        // Then
        assertEquals(1000L, fee)
    }

    @Test
    fun `Given a transaction with variable size When estimating fee Then the fee should be estimated based on network conditions`() {
        // Given
        val transactionSize = 500

        // When
        val feeEstimate = Wallet.estimateFee(transactionSize)

        // Then
        assertEquals(true, feeEstimate > 0)
    }

    @Test
    fun `Given a request for private key generation under low entropy When generating Then a secure private key should be produced`() {
        // Given
        val wallet = Wallet()

        // When
        val privateKey = wallet.generatePrivateKey()

        // Then
        assertEquals(true, wallet.isPrivateKeySecure(privateKey))
    }

    @Test
    fun `Given a transaction signing request When signing Then the transaction should be signed securely`() {
        // Given
        val wallet = Wallet()
        val transaction = Transaction(5000L)

        // When
        val signedTransaction = wallet.signTransaction(transaction)

        // Then
        assertEquals(true, signedTransaction.isSecure())
    }

    @Test
    fun `Given a simulated network failure When executing transaction Then the system should retry successfully`() {
        // Given
        val wallet = Wallet()
        val transaction = Transaction(5000L)

        // Simulate network failure
        wallet.simulateNetworkFailure()

        // When
        val retrySuccess = wallet.retryTransaction(transaction)

        // Then
        assertEquals(true, retrySuccess)
    }

    @Test
    fun `Given an interrupted transaction operation When recovering Then the system should restore transaction state`() {
        // Given
        val wallet = Wallet()
        val transaction = Transaction(5000L)

        // Simulate interruption
        wallet.simulateInterruption()

        // When
        val recoverySuccess = wallet.recoverTransaction(transaction)

        // Then
        assertEquals(true, recoverySuccess)
    }

    @Test
    fun `Given transaction inputs and outputs When verifying consistency Then the transaction should be confirmed as consistent`() {
        // Given
        val transaction = Transaction(inputs = 15000L, outputs = 14800L, fee = 200L)

        // When
        val isConsistent = transaction.verifyConsistency()

        // Then
        assertEquals(true, isConsistent)
    }
}
