package com.example.bitcoin.test

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import com.example.bitcoin.Wallet
import com.example.bitcoin.UTXO

class WalletTest {
    private lateinit var wallet: Wallet
    private lateinit var utxo1: UTXO
    private lateinit var utxo2: UTXO
    private lateinit var utxo3: UTXO
    private lateinit var utxoNegative: UTXO
    private lateinit var utxoNull: UTXO?

    @BeforeEach
    fun setUp() {
        utxo1 = UTXO(5000L)
        utxo2 = UTXO(3000L)
        utxo3 = UTXO(2000L)
        utxoNegative = UTXO(-1000L)
        utxoNull = null
        wallet = Wallet(balanceSats = 0)
    }

    @Test
    fun `Functional_RefreshBalanceWithValidUTXOs`() {
        wallet.utxos = listOf(utxo1, utxo2, utxo3)
        wallet.refreshBalance()
        assertEquals(10000L, wallet.balanceSats)
    }

    @Test
    fun `Functional_RefreshBalanceWithEmptyUTXOs`() {
        wallet.utxos = emptyList()
        wallet.refreshBalance()
        assertEquals(0L, wallet.balanceSats)
    }

    @Test
    fun `Functional_RefreshBalanceWithNegativeUTXOValue`() {
        wallet.utxos = listOf(utxoNegative, utxo2, utxo3)
        wallet.refreshBalance()
        assertEquals(4000L, wallet.balanceSats)
    }

    @Test
    fun `Functional_RefreshBalanceWithLargeUTXOValues`() {
        val utxoLarge = UTXO(Long.MAX_VALUE)
        wallet.utxos = listOf(utxoLarge)
        wallet.refreshBalance()
        assertEquals(Long.MAX_VALUE, wallet.balanceSats)
    }

    @Test
    fun `Functional_RefreshBalanceWithUTXOsExceedingMaximumLongValue`() {
        val utxoLarge = UTXO(Long.MAX_VALUE)
        val utxoOverflow = UTXO(1L)
        wallet.utxos = listOf(utxoLarge, utxoOverflow)
        assertThrows(ArithmeticException::class.java) {
            wallet.refreshBalance()
        }
    }

    @Test
    fun `Functional_RefreshBalanceWithUTXOsHavingMixedValidity`() {
        wallet.utxos = listOf(utxo1, utxoNull, utxo3)
        wallet.refreshBalance()
        assertEquals(7000L, wallet.balanceSats)
    }
}