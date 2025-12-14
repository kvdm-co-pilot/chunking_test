package com.example.bitcoin.testing

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import com.example.bitcoin.Wallet
import com.example.bitcoin.UTXO

class WalletBalanceTest {

    private lateinit var wallet: Wallet

    @BeforeEach
    fun setUp() {
        wallet = Wallet()
    }

    @Test
    fun `Calculate Wallet Balance with Multiple UTXOs`() {
        // Given
        val utxos = listOf(
            UTXO("txid1", 5000),
            UTXO("txid2", 3000),
            UTXO("txid3", 2000)
        )
        wallet.utxos = utxos

        // When
        val actualBalance = wallet.refreshBalance()

        // Then
        val expectedBalance = 5000 + 3000 + 2000
        assertEquals(expectedBalance, actualBalance)
    }

    @Test
    fun `Calculate Wallet Balance with No UTXOs`() {
        // Given
        wallet.utxos = emptyList()

        // When
        val actualBalance = wallet.refreshBalance()

        // Then
        assertEquals(0, actualBalance)
    }

    @Test
    fun `Calculate Large Wallet Balance`() {
        // Given
        val utxos = listOf(
            UTXO("txid1", 210000000000)
        )
        wallet.utxos = utxos

        // When
        val actualBalance = wallet.refreshBalance()

        // Then
        assertEquals(210000000000, actualBalance)
    }

    @Test
    fun `Handle Negative UTXO Value`() {
        // Given
        val utxos = listOf(
            UTXO("txid1", 5000),
            UTXO("txid2", -5000)
        )
        wallet.utxos = utxos

        // When
        val exception = assertThrows<IllegalArgumentException> {
            wallet.refreshBalance()
        }

        // Then
        assertEquals("Invalid UTXO amount", exception.message)
    }

    @Test
    fun `Handle UTXO Overflow`() {
        // Given
        val utxos = listOf(
            UTXO("txid1", Long.MAX_VALUE),
            UTXO("txid2", 1)
        )
        wallet.utxos = utxos

        // When
        val exception = assertThrows<IllegalStateException> {
            wallet.refreshBalance()
        }

        // Then
        assertEquals("Balance overflow", exception.message)
    }

    @Test
    fun `Handle Empty Wallet Initialization`() {
        // Given
        wallet.utxos = emptyList()

        // When
        val actualBalance = wallet.refreshBalance()

        // Then
        assertEquals(0, actualBalance)
    }
}
