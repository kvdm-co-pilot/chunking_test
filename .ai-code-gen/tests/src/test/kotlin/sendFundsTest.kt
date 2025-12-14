package com.example.bitcoin.testing

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import com.example.bitcoin.WalletService
import com.example.bitcoin.Transaction
import com.example.bitcoin.Address

class SendFundsTest {

    private lateinit var walletService: WalletService
    private val walletId = "wallet123"
    private val destinationAddress = Address("addressABC")

    @BeforeEach
    fun setUp() {
        walletService = WalletService()
        // Initialize wallet with mock data
        walletService.addWallet(walletId, 100000L, listOf("utxo1", "utxo2"))
    }

    @Test
    fun `Given sufficient UTXOs, When sending funds, Then transaction is created successfully`() {
        // Given
        val amountSats = 50000L
        val expectedFee = 1000L  // As per feeEstimator logic
        val expectedChange = 49000L  // Sum of UTXOs (100000) - amountSats (50000) - fee (1000)

        // When
        val transaction = walletService.sendFunds(walletId, destinationAddress, amountSats)

        // Then
        assertNotNull(transaction)
        assertEquals(expectedFee, transaction.fee)
        assertEquals(expectedChange, transaction.change)
    }

    @Test
    fun `Given no spendable UTXOs, When sending funds, Then error is thrown`() {
        // Given
        val amountSats = 500L

        // When
        val exception = assertThrows(IllegalStateException::class.java) {
            walletService.sendFunds(walletId, destinationAddress, amountSats)
        }

        // Then
        assertEquals("Insufficient spendable UTXOs", exception.message)
    }

    @Test
    fun `Given non-existent walletId, When sending funds, Then error is thrown`() {
        // Given
        val nonExistentWalletId = "walletXYZ"
        val amountSats = 10000L

        // When
        val exception = assertThrows(IllegalStateException::class.java) {
            walletService.sendFunds(nonExistentWalletId, destinationAddress, amountSats)
        }

        // Then
        assertEquals("Wallet not found", exception.message)
    }

    @Test
    fun `Given exact balance spend, When sending funds, Then transaction is created with no change`() {
        // Given
        val exactAmountSats = 50000L
        val expectedFee = 1000L
        val expectedChange = 0L

        // When
        val transaction = walletService.sendFunds(walletId, destinationAddress, exactAmountSats)

        // Then
        assertNotNull(transaction)
        assertEquals(expectedFee, transaction.fee)
        assertEquals(expectedChange, transaction.change)
    }

    @Test
    fun `Given negative transaction amount, When sending funds, Then error is thrown`() {
        // Given
        val negativeAmountSats = -10000L

        // When
        val exception = assertThrows(IllegalArgumentException::class.java) {
            walletService.sendFunds(walletId, destinationAddress, negativeAmountSats)
        }

        // Then
        assertEquals("Invalid amount", exception.message)
    }

    @Test
    fun `Given large transaction, When sending funds, Then transaction is created with expected fee and change`() {
        // Given
        val largeAmountSats = 900000L
        val expectedFee = 5000L
        val expectedChange = 95000L

        // When
        val transaction = walletService.sendFunds(walletId, Address("addressLMN"), largeAmountSats)

        // Then
        assertNotNull(transaction)
        assertEquals(expectedFee, transaction.fee)
        assertEquals(expectedChange, transaction.change)
    }
}
