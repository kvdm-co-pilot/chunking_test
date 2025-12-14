package com.example.bitcoin.testing

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import com.example.bitcoin.WalletService
import com.example.bitcoin.FeeEstimator
import com.example.bitcoin.Utxo
import com.example.bitcoin.Address
import com.example.bitcoin.Transaction

class Com_example_bitcoin_WalletServiceTest {

    private lateinit var walletService: WalletService
    private lateinit var mockFeeEstimator: FeeEstimator

    @BeforeEach
    fun setUp() {
        mockFeeEstimator = MockFeeEstimator()
        walletService = WalletService(mockFeeEstimator)
    }

    @Test
    fun `Given a valid label When creating a wallet Then it should create a wallet with unique ID, address, and zero balance`() {
        // Given
        val label = "MyWallet"

        // When
        val wallet = walletService.createWallet(label)

        // Then
        assertNotNull(wallet.id)
        assertTrue(wallet.address.value.startsWith("bc1"))
        assertEquals(0, wallet.balanceSats)
    }

    @Test
    fun `Given a valid UTXO When receiving funds Then it should add UTXO and update balance`() {
        // Given
        val wallet = walletService.createWallet("MyWallet")
        val utxo = Utxo(txId = "abc123", index = 0, amountSats = 100000)

        // When
        walletService.receiveFunds(wallet.id, utxo)

        // Then
        assertEquals(100000, wallet.balanceSats)
        assertTrue(wallet.utxos.contains(utxo))
    }

    @Test
    fun `Given sufficient balance When sending funds Then it should create transaction and update balance`() {
        // Given
        val wallet = walletService.createWallet("MyWallet")
        val utxo = Utxo(txId = "abc123", index = 0, amountSats = 100000)
        walletService.receiveFunds(wallet.id, utxo)
        val address = Address("bc1xyz")

        // When
        val transaction = walletService.sendFunds(wallet.id, address, 50000)

        // Then
        assertNotNull(transaction)
        assertEquals(50000, transaction.outputs.first().amountSats)
        assertEquals(50000, wallet.balanceSats) // Expected: 100000 - 50000 - fee = 50000
    }

    @Test
    fun `Given insufficient balance When sending funds Then it should raise insufficient spendable UTXOs error`() {
        // Given
        val wallet = walletService.createWallet("MyWallet")
        val address = Address("bc1xyz")

        // When
        val exception = assertThrows<IllegalStateException> {
            walletService.sendFunds(wallet.id, address, 200000)
        }

        // Then
        assertEquals("Insufficient spendable UTXOs", exception.message)
    }

    @Test
    fun `Given non-existent wallet When performing an operation Then it should raise wallet not found error`() {
        // Given
        val walletId = "wallet-999"
        val address = Address("bc1xyz")

        // When
        val exception = assertThrows<IllegalArgumentException> {
            walletService.sendFunds(walletId, address, 50000)
        }

        // Then
        assertEquals("Wallet not found", exception.message)
    }

    inner class MockFeeEstimator : FeeEstimator {
        override fun estimateFee(amountSats: Long): Long {
            return 1000 // Mock fee for testing
        }
    }
}
