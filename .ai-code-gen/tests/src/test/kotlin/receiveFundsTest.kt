package com.example.bitcoin.tests

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import com.example.bitcoin.WalletService
import com.example.bitcoin.Utxo

class WalletServiceReceiveFundsTest {

    @Test
    fun `Given valid walletId and UTXO When receiveFunds is called Then wallet balance increases by UTXO value`() {
        // Given
        val walletService = WalletService()
        val walletId = "wallet_123"
        val utxo = Utxo(value = 0.5, valid = true)

        // When
        walletService.receiveFunds(walletId, utxo)

        // Then
        val expectedBalance = 0.5 // Assuming initial balance is 0
        assertEquals(expectedBalance, walletService.getBalance(walletId))
    }

    @Test
    fun `Given invalid walletId When receiveFunds is called Then wallet not found error is thrown`() {
        // Given
        val walletService = WalletService()
        val walletId = "wallet_999"
        val utxo = Utxo(value = 0.2, valid = true)

        // When & Then
        val exception = assertThrows<IllegalArgumentException> {
            walletService.receiveFunds(walletId, utxo)
        }
        assertEquals("Wallet not found", exception.message)
    }

    @Test
    fun `Given invalid or spent UTXO When receiveFunds is called Then invalid UTXO error is thrown`() {
        // Given
        val walletService = WalletService()
        val walletId = "wallet_456"
        val utxo = Utxo(value = 0.3, valid = false)

        // When & Then
        val exception = assertThrows<IllegalArgumentException> {
            walletService.receiveFunds(walletId, utxo)
        }
        assertEquals("Invalid UTXO", exception.message)
    }

    @Test
    fun `Given zero balance UTXO When receiveFunds is called Then wallet balance remains unchanged`() {
        // Given
        val walletService = WalletService()
        val walletId = "wallet_789"
        val utxo = Utxo(value = 0.0, valid = true)

        // When
        walletService.receiveFunds(walletId, utxo)

        // Then
        val expectedBalance = 0.0 // Assuming initial balance is 0
        assertEquals(expectedBalance, walletService.getBalance(walletId))
    }

    @Test
    fun `Given UTXO value that exceeds wallet's max balance When receiveFunds is called Then balance limit exceeded error is thrown`() {
        // Given
        val walletService = WalletService()
        val walletId = "wallet_max"
        val utxo = Utxo(value = 10.0, valid = true)

        // When & Then
        val exception = assertThrows<IllegalArgumentException> {
            walletService.receiveFunds(walletId, utxo)
        }
        assertEquals("Balance limit exceeded", exception.message)
    }

    @Test
    fun `Given UTXO with negative value When receiveFunds is called Then UTXO value cannot be negative error is thrown`() {
        // Given
        val walletService = WalletService()
        val walletId = "wallet_123"
        val utxo = Utxo(value = -0.1, valid = true)

        // When & Then
        val exception = assertThrows<IllegalArgumentException> {
            walletService.receiveFunds(walletId, utxo)
        }
        assertEquals("UTXO value cannot be negative", exception.message)
    }
}
