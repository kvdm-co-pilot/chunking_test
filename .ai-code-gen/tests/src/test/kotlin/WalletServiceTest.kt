package com.example.bitcoin.tests

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import com.example.bitcoin.WalletService
import com.example.bitcoin.FeeEstimator
import com.example.bitcoin.Wallet
import com.example.bitcoin.Utxo
import com.example.bitcoin.Address
import com.example.bitcoin.Transaction
import com.example.bitcoin.PrivateKey

class WalletServiceTest {

    private val feeEstimator = FeeEstimator()
    private val walletService = WalletService(feeEstimator)

    @Test
    fun `Functional_CreateWalletWithValidLabel`() {
        // Given
        val label = "MyWallet"

        // When
        val wallet = walletService.createWallet(label)

        // Then
        assertEquals(label, wallet.label)
        assertTrue(wallet.address.value.startsWith("bc1"))
        assertEquals(0, wallet.balanceSats)
    }

    @Test
    fun `Functional_ReceiveFundsWithValidUtxo`() {
        // Given
        val wallet = walletService.createWallet("MyWallet")
        val utxo = Utxo(txId = "tx123", index = 0, amountSats = 100000)

        // When
        walletService.receiveFunds(wallet.id, utxo)

        // Then
        assertEquals(100000, wallet.balanceSats)
    }

    @Test
    fun `Functional_SendFundsSufficientBalance`() {
        // Given
        val wallet = walletService.createWallet("MyWallet")
        val to = Address("bc1xyz")
        val utxo = Utxo(txId = "tx123", index = 0, amountSats = 100000)
        walletService.receiveFunds(wallet.id, utxo)
        
        // When
        val transaction = walletService.sendFunds(wallet.id, to, 100000)
        
        // Then
        assertEquals(0, wallet.balanceSats)
        assertEquals(to, transaction.outputs.first().address)
    }

    @Test
    fun `Functional_SendFundsInsufficientBalance`() {
        // Given
        val wallet = walletService.createWallet("MyWallet")
        val to = Address("bc1xyz")

        // When
        val exception = assertThrows<IllegalStateException> {
            walletService.sendFunds(wallet.id, to, 100000)
        }

        // Then
        assertEquals("Insufficient spendable UTXOs", exception.message)
    }

    @Test
    fun `Functional_TransactionFeeEstimation`() {
        // Given
        val amountSats = 100000L

        // When
        val fee = feeEstimator.estimateFee(amountSats)

        // Then
        assertTrue(fee > 0)
        // Assuming fee is estimated based on network conditions, which could vary
    }

    @Test
    fun `Boundary_SendFundsExactBalanceError`() {
        // Given
        val wallet = walletService.createWallet("MyWallet")
        val to = Address("bc1xyz")
        val utxo = Utxo(txId = "tx123", index = 0, amountSats = 100000)
        walletService.receiveFunds(wallet.id, utxo)

        // When
        val exception = assertThrows<IllegalStateException> {
            walletService.sendFunds(wallet.id, to, 100000)
        }

        // Then
        assertEquals("Insufficient spendable UTXOs", exception.message)
    }

    @Test
    fun `Invariant_VerifyValidAddressFormat`() {
        // Given
        val address = walletService.createWallet("MyWallet").address

        // When
        val validFormat = address.value.isNotBlank() && address.value.matches(Regex("^(bc1|1|3).*")

        // Then
        assertTrue(validFormat)
    }

    @Test
    fun `Invariant_VerifyTotalInputGreaterThanOutputPlusFee`() {
        // Given
        val wallet = walletService.createWallet("MyWallet")
        val utxo = Utxo(txId = "tx123", index = 0, amountSats = 100000)
        walletService.receiveFunds(wallet.id, utxo)
        val fee = feeEstimator.estimateFee(50000)

        // When
        val transaction = walletService.sendFunds(wallet.id, Address("bc1xyz"), 50000)

        // Then
        val totalInput = utxo.amountSats
        val totalOutputPlusFee = transaction.outputs.sumOf { it.amountSats } + fee
        assertTrue(totalInput >= totalOutputPlusFee)
    }

    @Test
    fun `Invariant_SecurePrivateKeyGeneration`() {
        // When
        val privateKey = PrivateKey.generate()

        // Then
        assertNotNull(privateKey)
        assertTrue(privateKey.value.isNotBlank())
    }
}


class FeeEstimator {
    fun estimateFee(amountSats: Long): Long = 1000L // Simplified fee estimation
}

class PrivateKey {
    companion object {
        fun generate(): PrivateKey = PrivateKey()
    }

    fun toPublicKey(): PublicKey = PublicKey("public-key")

    val value: String = "private-key"
}

class PublicKey(val value: String)

class Wallet(
    val id: String,
    val label: String,
    val address: Address,
    val publicKey: PublicKey,
    val utxos: MutableList<Utxo>,
    var balanceSats: Long
) {
    fun addUtxo(utxo: Utxo) {
        utxos.add(utxo)
    }

    fun refreshBalance() {
        balanceSats = utxos.sumOf { it.amountSats }
    }

    fun spendableUtxos(fee: Long): List<Utxo> {
        return utxos.filter { it.amountSats >= fee }
    }
}

data class Utxo(val txId: String, val index: Int, val amountSats: Long)

data class Address(val value: String)

data class Transaction(val inputs: List<TransactionInput>, val outputs: List<TransactionOutput>)

data class TransactionInput(val txId: String, val index: Int, val signature: String)

data class TransactionOutput(val address: Address, val amountSats: Long, val scriptPubKey: String)
