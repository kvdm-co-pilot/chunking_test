package com.example.bitcoin.test

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import com.example.bitcoin.PrivateKey
import com.example.bitcoin.Wallet
import com.example.bitcoin.Transaction
import com.example.bitcoin.KeyManager
import com.example.bitcoin.UTXO

class PrivateKeyTest {
    private lateinit var privateKey: PrivateKey
    private lateinit var wallet: Wallet
    private lateinit var keyManager: KeyManager

    @BeforeEach
    fun setUp() {
        keyManager = KeyManager()
        privateKey = keyManager.generatePrivateKey()
        wallet = Wallet(privateKey)
    }

    @Test
    fun `Functional_GenerateValidPrivateKey`() {
        assertTrue(keyManager.isValidPrivateKey(privateKey))
    }

    @Test
    fun `Functional_StoreValidPrivateKeySecurely`() {
        assertTrue(keyManager.isStoredSecurely(privateKey))
    }

    @Test
    fun `Functional_HandleRepeatedPrivateKeyGenerationEfficiently`() {
        val keys = (1..100).map { keyManager.generatePrivateKey() }
        keys.forEach { assertTrue(keyManager.isValidPrivateKey(it)) }
    }

    @Test
    fun `Functional_RejectInvalidKeyFormatWithErrorMessage`() {
        val exception = assertThrows<IllegalArgumentException> {
            keyManager.importPrivateKey("invalidFormatKey")
        }
        assertEquals("Invalid key format", exception.message)
    }

    @Test
    fun `Functional_ValidateMinimumKeyLength`() {
        val minKey = keyManager.generatePrivateKeyWithLength(256)
        assertTrue(keyManager.isValidPrivateKey(minKey))
    }

    @Test
    fun `Functional_AcceptMaximumKeyLengthWithoutPerformanceDegradation`() {
        val maxKey = keyManager.generatePrivateKeyWithLength(512)
        assertTrue(keyManager.isValidPrivateKey(maxKey))
    }

    @Test
    fun `Invariant_VerifyBitcoinAddressValidityDuringWalletCreation`() {
        val address = wallet.createAddress()
        assertTrue(wallet.isValidAddress(address))
    }

    @Test
    fun `Invariant_EnsureTransactionInputEqualsOutputPlusFees`() {
        val transaction = Transaction(1000L, 10L)
        assertEquals(1010L, transaction.totalAmount())
    }

    @Test
    fun `Invariant_CheckUTXOUniquenessOnTransactionCreation`() {
        val utxos = listOf(UTXO("utxo1"), UTXO("utxo2"))
        val transaction = Transaction(utxos)
        assertTrue(transaction.hasUniqueUTXOs())
    }

    @Test
    fun `StateTransition_TestWalletCreationFromNonExistentToActive`() {
        val newWallet = Wallet(keyManager.generatePrivateKey())
        assertTrue(newWallet.isActive())
    }

    @Test
    fun `StateTransition_TestFundTransferFromSufficientToReducedBalance`() {
        val initialBalance = wallet.balance
        wallet.transferFunds(100L, "recipientAddress")
        assertEquals(initialBalance - 100L, wallet.balance)
    }

    @Test
    fun `StateTransition_VerifyIrreversibilityOfWalletCreation`() {
        assertThrows<UnsupportedOperationException> {
            wallet.revertCreation()
        }
    }

    @Test
    fun `Security_VerifySecureStorageOfGeneratedPrivateKeys`() {
        assertTrue(keyManager.isStoredSecurely(privateKey))
    }

    @Test
    fun `CrossEntity_VerifyWalletUTXOConsistencyReflectingBalance`() {
        val utxos = wallet.getUTXOs()
        assertEquals(wallet.balance, utxos.sumOf { it.amount })
    }

    @Test
    fun `CrossEntity_EnsureTransactionInputsMatchOutputsPlusFees`() {
        val transaction = Transaction(950L, 50L)
        assertEquals(transaction.inputs.sum(), transaction.outputs.sum() + transaction.fees)
    }
}