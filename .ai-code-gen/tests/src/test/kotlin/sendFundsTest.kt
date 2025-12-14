package com.example.bitcoin.test

import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import com.example.bitcoin.WalletService
import com.example.bitcoin.Transaction
import com.example.bitcoin.Address
import com.example.bitcoin.TransactionInput
import com.example.bitcoin.TransactionOutput

class WalletServiceTest {
    private lateinit var walletService: WalletService

    @BeforeEach
    fun setUp() {
        walletService = WalletService()
    }

    @Test
    fun `Functional_SendFunds_Success_WithSufficientBalance`() {
        val walletId = "wallet123"
        val to = Address("addressABC")
        val amountSats = 50000L
        val fee = 2000L

        val transaction: Transaction = walletService.sendFunds(walletId, to, amountSats)

        assertEquals(1, transaction.inputs.size)
        assertEquals(2, transaction.outputs.size)
        assertEquals(to, transaction.outputs[0].address)
        assertEquals(walletService.wallets[walletId]?.address, transaction.outputs[1].address)
    }

    @Test
    fun `Functional_SendFunds_InsufficientUtxos`() {
        val walletId = "wallet123"
        val to = Address("addressABC")
        val amountSats = 50000L
        val fee = 2000L

        assertThrows(Exception::class.java) {
            walletService.sendFunds(walletId, to, amountSats)
        }
    }

    @Test
    fun `Functional_SendFunds_ExactBalance_NoChange`() {
        val walletId = "wallet123"
        val to = Address("addressABC")
        val amountSats = 50000L
        val fee = 0L

        val transaction: Transaction = walletService.sendFunds(walletId, to, amountSats)

        assertEquals(1, transaction.inputs.size)
        assertEquals(1, transaction.outputs.size)
        assertEquals(to, transaction.outputs[0].address)
    }

    @Test
    fun `Functional_SendFunds_NoWalletFound`() {
        val walletId = "walletDoesNotExist"
        val to = Address("addressABC")
        val amountSats = 10000L

        assertThrows(Exception::class.java) {
            walletService.sendFunds(walletId, to, amountSats)
        }
    }

    @Test
    fun `Functional_SendFunds_MaxFeeExceedsBalance`() {
        val walletId = "wallet123"
        val to = Address("addressABC")
        val amountSats = 8000L
        val fee = 12000L

        assertThrows(Exception::class.java) {
            walletService.sendFunds(walletId, to, amountSats)
        }
    }

    @Test
    fun `Invariant_VerifyValidBitcoinAddress_WhenSendingFunds`() {
        val walletId = "wallet123"
        val to = Address("invalidAddress")
        val amountSats = 1000L

        assertThrows(Exception::class.java) {
            walletService.sendFunds(walletId, to, amountSats)
        }
    }

    @Test
    fun `Invariant_VerifyTransactionInputOutputBalance_WithFees`() {
        val walletId = "wallet123"
        val to = Address("addressABC")
        val amountSats = 50000L
        val fee = 1000L

        val transaction: Transaction = walletService.sendFunds(walletId, to, amountSats)

        val totalInputSats = transaction.inputs.sumOf { it.amountSats }
        val totalOutputSats = transaction.outputs.sumOf { it.amountSats }

        assertEquals(totalInputSats, totalOutputSats + fee)
    }
}
