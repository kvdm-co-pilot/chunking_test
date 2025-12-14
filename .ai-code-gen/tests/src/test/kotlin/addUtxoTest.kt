package com.example.bitcoin.testing

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Disabled
import com.example.bitcoin.Wallet

class WalletBalanceTest {

    private lateinit var wallet: Wallet

    @BeforeEach
    fun setUp() {
        wallet = Wallet()
    }

    @Test
    fun `Given a Wallet with 100000 sats, When displaying balance, Then it should show 100000 sats`() {
        // Given
        wallet.balanceSats = 100000
        val expectedBalance = 100000

        // When
        val actualBalance = wallet.displayBalance()

        // Then
        assertEquals(expectedBalance, actualBalance)
    }

    @Test
    fun `Given a Wallet with -500 sats, When displaying balance, Then it should show error message for negative balance`() {
        // Given
        wallet.balanceSats = -500
        val expectedMessage = "Balance cannot be negative"

        // When
        val exception = assertThrows<IllegalArgumentException> {
            wallet.displayBalance()
        }

        // Then
        assertEquals(expectedMessage, exception.message)
    }

    @Test
    fun `Given a Wallet with 0 sats, When displaying balance, Then it should show 0 sats`() {
        // Given
        wallet.balanceSats = 0
        val expectedBalance = 0

        // When
        val actualBalance = wallet.displayBalance()

        // Then
        assertEquals(expectedBalance, actualBalance)
    }

    @Test
    fun `Given a Wallet with 2100000000000000 sats, When displaying balance, Then it should show 2100000000000000 sats`() {
        // Given
        wallet.balanceSats = 2100000000000000
        val expectedBalance = 2100000000000000

        // When
        val actualBalance = wallet.displayBalance()

        // Then
        assertEquals(expectedBalance, actualBalance)
    }

    @Test
    fun `Given a Wallet with corrupted balance 'abc', When displaying balance, Then it should show error message for invalid format`() {
        // Given
        wallet.balanceSats = "abc" // Simulating corrupted input
        val expectedMessage = "Invalid balance format"

        // When
        val exception = assertThrows<IllegalArgumentException> {
            wallet.displayBalance()
        }

        // Then
        assertEquals(expectedMessage, exception.message)
    }

    @Test
    fun `Given a Wallet with 1 satoshi, When displaying balance, Then it should show 1 sat`() {
        // Given
        wallet.balanceSats = 1
        val expectedBalance = 1

        // When
        val actualBalance = wallet.displayBalance()

        // Then
        assertEquals(expectedBalance, actualBalance)
    }

    @Test
    fun `Given a Wallet with 546 sats at dust limit, When displaying balance, Then it should recognize dust limit correctly`() {
        // Given
        wallet.balanceSats = 546
        val expectedBalance = 546

        // When
        val actualBalance = wallet.displayBalance()

        // Then
        assertEquals(expectedBalance, actualBalance)
    }
}

