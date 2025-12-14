package com.example.bitcoin.tests

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import com.example.bitcoin.Wallet
import com.example.bitcoin.Address
import com.example.bitcoin.Utxo
import com.example.bitcoin.PublicKey
import java.lang.IllegalArgumentException

class WalletBalanceTest {

    private val testAddress = Address("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa")
    private val testPublicKey = PublicKey("testPublicKey")

    @Test
    fun `Given a Wallet with multiple UTXOs When refreshing balance Then balance should be correctly calculated`() {
        // Given
        val utxos = mutableListOf(Utxo(1000), Utxo(2000), Utxo(3000))
        val wallet = Wallet("wallet1", "Test Wallet", testAddress, testPublicKey, utxos, 0)

        // When
        val balance = wallet.refreshBalance()

        // Then
        assertEquals(6000L, balance)
    }

    @Test
    fun `Given a Wallet with no UTXOs When refreshing balance Then balance should be zero`() {
        // Given
        val wallet = Wallet("wallet2", "Empty Wallet", testAddress, testPublicKey, mutableListOf(), 0)

        // When
        val balance = wallet.refreshBalance()

        // Then
        assertEquals(0L, balance)
    }

    @Test
    fun `Given a Wallet with a single UTXO When refreshing balance Then balance should match single UTXO value`() {
        // Given
        val utxos = mutableListOf(Utxo(1500))
        val wallet = Wallet("wallet3", "Single UTXO Wallet", testAddress, testPublicKey, utxos, 0)

        // When
        val balance = wallet.refreshBalance()

        // Then
        assertEquals(1500L, balance)
    }

    @Test
    fun `Given a Wallet with negative UTXO value When refreshing balance Then balance should be calculated correctly`() {
        // Given
        val utxos = mutableListOf(Utxo(-1000), Utxo(2000))
        val wallet = Wallet("wallet4", "Negative UTXO Wallet", testAddress, testPublicKey, utxos, 0)

        // When
        val balance = wallet.refreshBalance()

        // Then
        assertEquals(1000L, balance)
    }

    @Test
    fun `Given a Wallet with a large number of UTXOs When refreshing balance Then balance should match the total UTXOs value`() {
        // Given
        val utxos = MutableList(10000) { Utxo(1) }
        val wallet = Wallet("wallet5", "Large UTXO Wallet", testAddress, testPublicKey, utxos, 0)

        // When
        val balance = wallet.refreshBalance()

        // Then
        assertEquals(10000L, balance)
    }

    @Test
    fun `Given a Wallet with UTXOs summing to Long_MAX_VALUE When refreshing balance Then balance should match Long_MAX_VALUE`() {
        // Given
        val utxos = mutableListOf(Utxo(Long.MAX_VALUE))
        val wallet = Wallet("wallet6", "Max Value Wallet", testAddress, testPublicKey, utxos, 0)

        // When
        val balance = wallet.refreshBalance()

        // Then
        assertEquals(Long.MAX_VALUE, balance)
    }

    @Test
    fun `Given a Wallet with invalid UTXO amount When refreshing balance Then error should be thrown`() {
        // Given
        val utxos = mutableListOf(Utxo(null))
        val wallet = Wallet("wallet7", "Invalid UTXO Wallet", testAddress, testPublicKey, utxos, 0)

        // When & Then
        val exception = assertThrows<IllegalArgumentException> {
            wallet.refreshBalance()
        }
        assertEquals("Invalid UTXO amount", exception.message)
    }

    @Test
    fun `Given a Wallet with blank address When refreshing balance Then error should be thrown`() {
        // Given
        val wallet = Wallet("wallet8", "Blank Address Wallet", Address(""), testPublicKey, mutableListOf(), 0)

        // When & Then
        val exception = assertThrows<IllegalArgumentException> {
            wallet.refreshBalance()
        }
        assertEquals("Address cannot be blank", exception.message)
    }
}
