package com.example.bitcoin.tests

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import com.example.bitcoin.Wallet
import com.example.bitcoin.UTXO
import com.example.bitcoin.Address

class WalletBalanceTest {

    @Test
    fun `Given multiple UTXOs When calculating balance Then balance should be correct`() {
        // Given
        val utxos = listOf(
            UTXO(amountSats = 5000L),
            UTXO(amountSats = 12000L),
            UTXO(amountSats = 30000L)
        )
        val wallet = Wallet(balanceSats = 0L)
        wallet.utxos = utxos

        // When
        val balance = wallet.refreshBalance()

        // Then
        assertEquals(47000L, balance)
    }

    @Test
    fun `Given zero UTXOs When calculating balance Then balance should be zero`() {
        // Given
        val wallet = Wallet(balanceSats = 0L)
        wallet.utxos = emptyList()

        // When
        val balance = wallet.refreshBalance()

        // Then
        assertEquals(0L, balance)
    }

    @Test
    fun `Given a single UTXO When calculating balance Then balance should match UTXO amount`() {
        // Given
        val utxos = listOf(UTXO(amountSats = 10000L))
        val wallet = Wallet(balanceSats = 0L)
        wallet.utxos = utxos

        // When
        val balance = wallet.refreshBalance()

        // Then
        assertEquals(10000L, balance)
    }

    @Test
    fun `Given large UTXO amounts When calculating balance Then balance should handle large values`() {
        // Given
        val utxos = listOf(
            UTXO(amountSats = 9223372036854775807L),
            UTXO(amountSats = 1000L)
        )
        val wallet = Wallet(balanceSats = 0L)
        wallet.utxos = utxos

        // When
        val balance = wallet.refreshBalance()

        // Then
        assertEquals(9223372036854776807L, balance)
    }

    @Test
    fun `Given negative UTXO amounts When calculating balance Then exception should be thrown`() {
        // Given
        val utxos = listOf(
            UTXO(amountSats = -5000L),
            UTXO(amountSats = 12000L)
        )
        val wallet = Wallet(balanceSats = 0L)
        wallet.utxos = utxos

        // When
        val exception = assertThrows<IllegalArgumentException> {
            wallet.refreshBalance()
        }

        // Then
        assertEquals("Invalid UTXO amounts.", exception.message)
    }

    @Test
    fun `Given a blank address When verifying Then exception should be thrown`() {
        // Given
        val address = Address("")

        // When
        val exception = assertThrows<IllegalArgumentException> {
            address.verify()
        }

        // Then
        assertEquals("Address cannot be blank.", exception.message)
    }
}
