package com.example.bitcoin.test

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import com.example.bitcoin.Wallet
import com.example.bitcoin.Transaction
import com.example.bitcoin.Address
import com.example.bitcoin.UTXO
import com.example.bitcoin.PrivateKey
import com.example.bitcoin.TransactionFeeCalculator
import java.lang.Exception

class WalletTest {
    private lateinit var wallet: Wallet
    private lateinit var recipientAddress: Address
    private lateinit var privateKey: PrivateKey
    
    @BeforeEach
    fun setUp() {
        wallet = Wallet(privateKey, listOf(UTXO(1_000_000L))) // 1 BTC
        recipientAddress = Address("validRecipientAddress")
        privateKey = PrivateKey("samplePrivateKey")
    }
    
    @Test
    fun `Functional_ValidTransactionProcessing_0.5BTC`() {
        val transaction = wallet.createTransaction(0.5, recipientAddress)
        wallet.processTransaction(transaction)
        
        assertEquals(500_000L, wallet.balanceSats) // Expect balance reduced by 0.5 BTC
        assertEquals(0.5, recipientAddress.balanceBTC) // Expect recipient balance increased by 0.5 BTC
    }
    
    @Test
    fun `Functional_RejectTransaction_InsufficientFunds_0.5BTC`() {
        wallet = Wallet(privateKey, listOf(UTXO(300_000L))) // 0.3 BTC
        
        val exception = assertThrows<Exception> {
            wallet.createTransaction(0.5, recipientAddress)
        }
        assertEquals("Insufficient funds", exception.message)
        assertEquals(300_000L, wallet.balanceSats) // Balance remains unchanged
    }
    
    @Test
    fun `Functional_RejectTransaction_InvalidAddressFormat`() {
        val invalidAddress = Address("invalidFormat")
        
        val exception = assertThrows<Exception> {
            wallet.createTransaction(0.5, invalidAddress)
        }
        assertEquals("Invalid address format", exception.message)
        assertEquals(1_000_000L, wallet.balanceSats) // Balance remains unchanged
    }
    
    @Test
    fun `Functional_RejectTransaction_ZeroBTC`() {
        val exception = assertThrows<Exception> {
            wallet.createTransaction(0.0, recipientAddress)
        }
        assertEquals("Invalid transaction amount", exception.message)
        assertEquals(1_000_000L, wallet.balanceSats) // Balance remains unchanged
    }
    
    @Test
    fun `Functional_ValidTransaction_MaximumLimit_10BTC`() {
        wallet = Wallet(privateKey, listOf(UTXO(10_000_000_000L))) // 100 BTC
        
        val transaction = wallet.createTransaction(10.0, recipientAddress)
        wallet.processTransaction(transaction)
        
        assertEquals(90_000_000_000L, wallet.balanceSats) // Expect balance reduced by 10 BTC
        assertEquals(10.0, recipientAddress.balanceBTC) // Expect recipient balance increased by 10 BTC
    }
    
    @Test
    fun `Invariant_VerifyBitcoinAddressValidity`() {
        val validAddresses = listOf(
            "1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa",
            "3J98t1WpEZ73CNmQviecrnyiWrnqRhWNLy",
            "bc1qxy2kgdygjrsqtzq2n0yrf2493p83kkfjhx0wlh"
        )
        
        validAddresses.forEach { address ->
            assertTrue(Address.isValid(address), "Address $address is invalid")
        }
    }
    
    @Test
    fun `Invariant_VerifyTransactionInputOutputEqualityPlusFees`() {
        val inputs = 1_000_000L // 1 BTC
        val outputs = 990_000L // 0.99 BTC
        val fees = TransactionFeeCalculator.calculateFee(inputs, outputs)
        
        assertEquals(inputs, outputs + fees, "Inputs do not equal outputs plus fees")
    }
    
    @Test
    fun `Invariant_VerifyUniqueUTXOUsage`() {
        val utxoSet = listOf(UTXO(1_000_000L), UTXO(500_000L), UTXO(500_000L))
        
        val usedUTXOs = wallet.useUTXOs(utxoSet)
        assertEquals(usedUTXOs.toSet().size, usedUTXOs.size, "UTXOs are not unique")
    }
    
    @Test
    fun `StateTransition_WalletCreation_NonExistentToActive`() {
        val newWallet = Wallet.createWallet(privateKey)
        
        assertTrue(newWallet.isActive, "Wallet did not transition to active status")
    }
    
    @Test
    fun `StateTransition_FundTransfer_SufficientToReducedBalance`() {
        val transaction = wallet.createTransaction(0.1, recipientAddress)
        wallet.processTransaction(transaction)
        
        assertEquals(900_000L, wallet.balanceSats) // Balance reduced by 0.1 BTC
        assertEquals(0.1, recipientAddress.balanceBTC) // Recipient balance increased by 0.1 BTC
    }
    
    @Test
    fun `DomainBoundary_TransactionAmount_Minimum_1Sat`() {
        val transaction = wallet.createTransaction(0.00000001, recipientAddress) // 1 sat
        wallet.processTransaction(transaction)
        
        assertEquals(999_999L, wallet.balanceSats) // Balance reduced by 1 sat
        assertEquals(0.00000001, recipientAddress.balanceBTC) // Recipient balance increased by 1 sat
    }
    
    @Test
    fun `DomainBoundary_TransactionAmount_Maximum_21TrillionSat`() {
        wallet = Wallet(privateKey, listOf(UTXO(21_000_000_000_000L))) // 21 trillion satoshis
        
        val transaction = wallet.createTransaction(21000000000000.0, recipientAddress)
        wallet.processTransaction(transaction)
        
        assertEquals(0L, wallet.balanceSats) // Balance reduced by 21 trillion satoshis
        assertEquals(21000000000000.0, recipientAddress.balanceBTC) // Recipient balance increased by 21 trillion satoshis
    }
    
    @Test
    fun `DomainBoundary_TransactionAmount_DustLimit`() {
        val exception = assertThrows<Exception> {
            wallet.createTransaction(0.0000001, recipientAddress) // Below dust limit
        }
        assertEquals("Transaction amount below dust limit", exception.message)
    }
    
    @Test
    fun `DomainBoundary_FeeEstimation_MinimumRelayFee_1SatPerVbyte`() {
        val transaction = wallet.createTransaction(0.1, recipientAddress)
        val fee = TransactionFeeCalculator.estimateFee(transaction)
        
        assertEquals(fee, transaction.sizeInBytes * 1, "Fee estimation incorrect")
    }
    
    @Test
    fun `Security_PrivateKeyGeneration_PredictableRandomNumberGeneration`() {
        val generatedKey = PrivateKey.generateSecureKey()
        assertNotNull(generatedKey, "Generated key should not be null")
        
        val anotherGeneratedKey = PrivateKey.generateSecureKey()
        assertNotEquals(generatedKey, anotherGeneratedKey, "Random number generation should be unpredictable")
    }
    
    @Test
    fun `Security_PrivateKeyGeneration_InsecureStorage`() {
        val generatedKey = PrivateKey.generateSecureKey()
        
        assertTrue(PrivateKey.isStoredSecurely(generatedKey), "Private key storage is insecure")
    }
    
    @Test
    fun `Security_TransactionSigning_WeakCryptographicAlgorithms`() {
        val transaction = wallet.createTransaction(0.1, recipientAddress)
        
        assertTrue(transaction.isSignedWithStrongAlgorithm(), "Transaction signing uses weak algorithms")
    }
    
    @Test
    fun `Security_TransactionSigning_PrivateKeyExposure`() {
        val transaction = wallet.createTransaction(0.1, recipientAddress)
        wallet.signTransaction(transaction)
        
        assertFalse(transaction.exposesPrivateKey(), "Private key exposure during signing")
    }
    
    @Test
    fun `FailureRecovery_WalletCreation_InvalidAddressGeneration`() {
        val exception = assertThrows<Exception> {
            Wallet.createWalletWithInvalidAddress(privateKey)
        }
        assertEquals("Invalid address generation", exception.message)
    }
    
    @Test
    fun `FailureRecovery_WalletCreation_KeyGenerationFailure`() {
        val exception = assertThrows<Exception> {
            Wallet.createWalletWithKeyGenerationFailure()
        }
        assertEquals("Key generation failure", exception.message)
    }
    
    @Test
    fun `FailureRecovery_FundTransfer_InsufficientUTXOs`() {
        val exception = assertThrows<Exception> {
            wallet.createTransactionWithInsufficientUTXOs(0.5, recipientAddress)
        }
        assertEquals("Insufficient UTXOs", exception.message)
    }
    
    @Test
    fun `FailureRecovery_FundTransfer_FeeCalculationError`() {
        val exception = assertThrows<Exception> {
            wallet.createTransactionWithFeeCalculationError(0.1, recipientAddress)
        }
        assertEquals("Fee calculation error", exception.message)
    }
    
    @Test
    fun `CrossEntity_WalletToUTXOs_BalanceConsistency`() {
        val utxos = listOf(UTXO(500_000L), UTXO(500_000L))
        wallet = Wallet(privateKey, utxos)
        
        assertEquals(wallet.balanceSats, utxos.sumOf { it.amountSats }, "Wallet balance inconsistent with UTXOs")
    }
    
    @Test
    fun `CrossEntity_TransactionInputsToOutputs_Consistency`() {
        val transaction = wallet.createTransaction(0.5, recipientAddress)
        wallet.processTransaction(transaction)
        
        assertEquals(transaction.inputs.sum(), transaction.outputs.sum() + transaction.fees, "Transaction inputs/outputs inconsistency")
    }
}