package com.example.bitcoin.tests

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import com.example.bitcoin.Wallet
import com.example.bitcoin.Transaction

class WalletTest {

    @Test
    fun `Given valid credentials When creating a new wallet Then wallet is created successfully`() {
        // Given
        val username = "testUser"
        val password = "securePass123"

        // When
        val wallet = Wallet.create(username, password)

        // Then
        assertNotNull(wallet)
        assertTrue(wallet.isSecure())
        assertEquals("Wallet created successfully", wallet.confirmationMessage)
    }

    @Test
    fun `Given a valid Bitcoin address When adding funds Then wallet balance is updated correctly`() {
        // Given
        val wallet = Wallet("testUser", "securePass123")
        val bitcoinAddress = "1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa"
        val amount = 0.5

        // When
        wallet.addFunds(bitcoinAddress, amount)

        // Then
        assertEquals(0.5, wallet.balance)
        assertEquals("Funds added successfully", wallet.transactionConfirmation)
    }

    @Test
    fun `Given sufficient balance and valid recipient address When transferring funds Then transaction is processed successfully`() {
        // Given
        val wallet = Wallet("testUser", "securePass123")
        wallet.addFunds("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa", 0.5)
        val recipientAddress = "3J98t1WpEZ73CNmQviecrnyiWrnqRhWNLy"
        val amount = 0.3

        // When
        val transaction = wallet.transferFunds(recipientAddress, amount)

        // Then
        assertNotNull(transaction)
        assertEquals(0.2, wallet.balance)
        assertEquals("Transaction processed successfully", transaction.confirmationMessage)
    }

    @Test
    fun `Given insufficient balance When transferring funds Then error message is displayed`() {
        // Given
        val wallet = Wallet("testUser", "securePass123")
        val recipientAddress = "3FZbgi29cpjq2GjdwV8eyHuJJnkLtktZc5"
        val amount = 5.0

        // When
        val exception = assertThrows<IllegalArgumentException> {
            wallet.transferFunds(recipientAddress, amount)
        }

        // Then
        assertEquals("Insufficient balance", exception.message)
    }

    @Test
    fun `Given an invalid recipient address When transferring funds Then error message is displayed`() {
        // Given
        val wallet = Wallet("testUser", "securePass123")
        val recipientAddress = "123InvalidAddress"
        val amount = 0.1

        // When
        val exception = assertThrows<IllegalArgumentException> {
            wallet.transferFunds(recipientAddress, amount)
        }

        // Then
        assertEquals("Invalid Bitcoin address", exception.message)
    }

    @Test
    fun `Given blank Bitcoin address When verifying address validity Then error is thrown`() {
        // Given
        val address = ""

        // When
        val exception = assertThrows<IllegalArgumentException> {
            Wallet.verifyBitcoinAddress(address)
        }

        // Then
        assertEquals("Bitcoin address cannot be blank", exception.message)
    }

    @Test
    fun `Given transaction inputs and outputs When inputs cover outputs plus fees Then transaction is processed successfully`() {
        // Given
        val inputs = 1.0
        val outputs = 1.0
        val fee = 0.0005

        // When
        val transaction = Transaction.process(inputs, outputs, fee)

        // Then
        assertNotNull(transaction)
        assertEquals("Transaction processed", transaction.confirmationMessage)
    }

    @Test
    fun `Given key generation When generating private key Then key is generated securely`() {
        // Given
        val keyGenerationProcess = "Strong RNG"

        // When
        val privateKey = Wallet.generatePrivateKey(keyGenerationProcess)

        // Then
        assertTrue(privateKey.isSecure())
    }

    @Test
    fun `Given UTXO and transaction When UTXO is spent Then UTXO becomes spent`() {
        // Given
        val utxo = Wallet.UTXO(0.5)
        val transaction = Transaction(0.5)

        // When
        utxo.spend(transaction)

        // Then
        assertTrue(utxo.isSpent())
    }

    @Test
    fun `Given initial balance and added funds When adding funds Then balance is updated`() {
        // Given
        val wallet = Wallet("testUser", "securePass123")
        wallet.addFunds("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa", 1.0)
        val addedFunds = 0.5

        // When
        wallet.addFunds("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa", addedFunds)

        // Then
        assertEquals(1.5, wallet.balance)
    }

    @Test
    fun `Given valid Bitcoin address prefixes When verifying prefixes Then system accepts them`() {
        // Given
        val addresses = listOf("1ABCD...", "3XYZ...", "bc1QRS...")

        // When/Then
        addresses.forEach {
            assertTrue(Wallet.verifyBitcoinAddressPrefix(it))
        }
    }

    @Test
    fun `Given transaction size and fee rate When calculating minimum fee Then fee calculated correctly`() {
        // Given
        val transactionSize = 250
        val feeRate = 1 // satoshi per byte

        // When
        val minimumFee = Wallet.calculateTransactionFee(transactionSize, feeRate)

        // Then
        assertEquals(250, minimumFee)
    }

    @Test
    fun `Given maximum allowable transaction limit When transferring funds Then transaction processed successfully`() {
        // Given
        val wallet = Wallet("testUser", "securePass123")
        wallet.addFunds("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa", 21_000_000.0)
        val transferAmount = 21_000_000.0

        // When
        wallet.transferFunds("3J98t1WpEZ73CNmQviecrnyiWrnqRhWNLy", transferAmount)

        // Then
        assertEquals(0.0, wallet.balance)
    }

    @Test
    fun `Given zero balance When attempting transfer Then error message displayed`() {
        // Given
        val wallet = Wallet("testUser", "securePass123")
        val transferAmount = 0.1

        // When
        val exception = assertThrows<IllegalArgumentException> {
            wallet.transferFunds("3J98t1WpEZ73CNmQviecrnyiWrnqRhWNLy", transferAmount)
        }

        // Then
        assertEquals("Insufficient funds", exception.message)
    }

    @Test
    fun `Given strong RNG When generating private key Then keys generated securely`() {
        // Given
        val keyGenerationProcess = "Strong RNG"

        // When
        val privateKey = Wallet.generatePrivateKey(keyGenerationProcess)

        // Then
        assertTrue(privateKey.isSecure())
    }

    @Test
    fun `Given private key storage and transmission When verifying key exposure Then keys remain confidential`() {
        // Given
        val storage = "Secure Storage"
        val transmission = "Encrypted Transmission"

        // When
        val privateKeyExposure = Wallet.verifyKeyExposure(storage, transmission)

        // Then
        assertFalse(privateKeyExposure)
    }

    @Test
    fun `Given transaction signature data When verifying signature replay Then system prevents replay attacks`() {
        // Given
        val signatureData = "Signature123"

        // When
        val replayAttackPrevented = Wallet.verifySignatureReplay(signatureData)

        // Then
        assertTrue(replayAttackPrevented)
    }

    @Test
    fun `Given signing process with private key When verifying key compromise Then private key remains secure`() {
        // Given
        val signingProcess = "Secure Signing"

        // When
        val keyCompromised = Wallet.verifyKeyCompromise(signingProcess)

        // Then
        assertFalse(keyCompromised)
    }

    @Test
    fun `Given network connectivity loss during transaction When handling dependency failure Then transaction is paused`() {
        // Given
        val wallet = Wallet("testUser", "securePass123")
        wallet.addFunds("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa", 1.0)

        // When
        val transactionPaused = wallet.handleExternalDependencyFailure("Network connectivity loss")

        // Then
        assertTrue(transactionPaused)
        assertEquals("Transaction paused due to network issues", wallet.dependencyFailureMessage)
    }

    @Test
    fun `Given system crash during transaction When recovering transaction process Then system recovers transaction state`() {
        // Given
        val wallet = Wallet("testUser", "securePass123")
        wallet.addFunds("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa", 1.0)

        // When
        val transactionRecovery = wallet.recoverTransactionProcess("System crash")

        // Then
        assertTrue(transactionRecovery)
        assertEquals("Transaction state recovered", wallet.recoveryMessage)
    }

    @Test
    fun `Given transaction inputs and outputs with fees When verifying consistency Then inputs match outputs plus fees`() {
        // Given
        val input = 1.0
        val output = 0.999
        val fee = 0.001

        // When
        val transactionConsistent = Wallet.verifyTransactionConsistency(input, output, fee)

        // Then
        assertTrue(transactionConsistent)
    }
}
