package com.example.bitcoin.test

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import com.example.bitcoin.WalletService
import com.example.bitcoin.FeeEstimator
import com.example.bitcoin.Wallet
import com.example.bitcoin.Utxo
import com.example.bitcoin.Address
import com.example.bitcoin.PrivateKey
import com.example.bitcoin.Transaction

class WalletServiceTest {
    private lateinit var walletService: WalletService
    private lateinit var mockFeeEstimator: FeeEstimator

    @BeforeEach
    fun setUp() {
        mockFeeEstimator = MockFeeEstimator()
        walletService = WalletService(mockFeeEstimator)
    }

    @Test
    fun `Functional_CreateWalletWithValidLabel`() {
        val wallet = walletService.createWallet("Personal Wallet")
        assertEquals("wallet-1", wallet.id)
        assertEquals("Personal Wallet", wallet.label)
        assertEquals(0, wallet.balanceSats)
        assert(wallet.address.value.startsWith("bc1"))
    }

    @Test
    fun `Functional_ReceiveFundsUpdatesBalanceCorrectly`() {
        val wallet = walletService.createWallet("Personal Wallet")
        walletService.receiveFunds(wallet.id, Utxo("tx123", 0, 100000))
        assertEquals(100000, wallet.balanceSats)
    }

    @Test
    fun `Functional_SendFundsWithSufficientBalanceCreatesTransaction`() {
        val wallet = walletService.createWallet("Personal Wallet")
        walletService.receiveFunds(wallet.id, Utxo("tx123", 0, 140000))
        val transaction = walletService.sendFunds(wallet.id, Address("bc1qxyz"), 100000)

        assertEquals(1, transaction.inputs.size)
        assertEquals(2, transaction.outputs.size)
        assertEquals(40000, transaction.outputs.find { it.address == wallet.address }?.amountSats)
    }

    @Test
    fun `Functional_SendFundsWithInsufficientSpendableUtxosThrowsError`() {
        val wallet = walletService.createWallet("Personal Wallet")
        walletService.receiveFunds(wallet.id, Utxo("tx123", 0, 90000))
        val exception = assertThrows<IllegalStateException> {
            walletService.sendFunds(wallet.id, Address("bc1qxyz"), 100000)
        }
        assertEquals("Insufficient spendable UTXOs", exception.message)
    }

    @Test
    fun `Boundary_TransactionAmountAtMinimumDustLimit`() {
        val wallet = walletService.createWallet("Personal Wallet")
        walletService.receiveFunds(wallet.id, Utxo("tx123", 0, 1000))
        val transaction = walletService.sendFunds(wallet.id, Address("bc1qxyz"), 546)
        assertEquals(1, transaction.inputs.size)
        assertEquals(1, transaction.outputs.size)
    }

    @Test
    fun `Boundary_TransactionAmountAtMaximumBitcoinSupply`() {
        val wallet = walletService.createWallet("Personal Wallet")
        val exception = assertThrows<IllegalStateException> {
            walletService.sendFunds(wallet.id, Address("bc1qxyz"), 2100000000000000)
        }
        assertEquals("Insufficient spendable UTXOs", exception.message)
    }

    @Test
    fun `Failure_ReceiveFundsWithInvalidWalletIdThrowsError`() {
        val exception = assertThrows<IllegalStateException> {
            walletService.receiveFunds("invalid-wallet", Utxo("tx123", 0, 100000))
        }
        assertEquals("Wallet not found", exception.message)
    }

    // Mock class for FeeEstimator
    private class MockFeeEstimator : FeeEstimator {
        override fun estimateFee(amountSats: Long): Long {
            return 10000 // Fixed mock fee for testing
        }
    }
}
