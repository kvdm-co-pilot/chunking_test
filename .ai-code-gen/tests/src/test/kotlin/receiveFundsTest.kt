package com.example.bitcoin.testing

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import com.example.bitcoin.WalletService
import com.example.bitcoin.Utxo

class WalletServiceReceiveFundsTest {

    private lateinit var walletService: WalletService
    private val validWalletId = "wallet123"
    private val invalidWalletId = "invalid123"
    private val validUtxo = Utxo(0.5)
    private val invalidUtxo = Utxo(-0.1)
    private val zeroValueUtxo = Utxo(0.0)
    private val largeValueUtxo = Utxo(1000.0)
    private val maximumValueUtxo = Utxo(21000000.0)

    @BeforeEach
    fun setUp() {
        walletService = WalletService()
    }

    @Test
    fun `Given valid wallet ID and UTXO, When receiving funds, Then wallet balance updates correctly`() {
        // Given
        val expectedBalance = 0.5

        // When
        walletService.receiveFunds(validWalletId, validUtxo)

        // Then
        assertEquals(expectedBalance, walletService.getBalance(validWalletId))
    }

    @Test
    fun `Given invalid wallet ID, When receiving funds, Then an error is thrown`() {
        // Given
        val expectedMessage = "Invalid wallet ID"

        // When
        val exception = assertThrows<IllegalArgumentException> {
            walletService.receiveFunds(invalidWalletId, validUtxo)
        }

        // Then
        assertEquals(expectedMessage, exception.message)
    }

    @Test
    fun `Given invalid UTXO, When receiving funds, Then an error is thrown`() {
        // Given
        val expectedMessage = "Invalid UTXO value"

        // When
        val exception = assertThrows<IllegalArgumentException> {
            walletService.receiveFunds(validWalletId, invalidUtxo)
        }

        // Then
        assertEquals(expectedMessage, exception.message)
    }

    @Test
    fun `Given zero value UTXO, When receiving funds, Then wallet balance remains unchanged`() {
        // Given
        val expectedBalance = 0.0 // Assuming initial balance is 0

        // When
        walletService.receiveFunds(validWalletId, zeroValueUtxo)

        // Then
        assertEquals(expectedBalance, walletService.getBalance(validWalletId))
    }

    @Test
    fun `Given large value UTXO, When receiving funds, Then wallet balance updates correctly`() {
        // Given
        val expectedBalance = 1000.0

        // When
        walletService.receiveFunds(validWalletId, largeValueUtxo)

        // Then
        assertEquals(expectedBalance, walletService.getBalance(validWalletId))
    }

    @Test
    fun `Given maximum permissible UTXO value, When receiving funds, Then wallet balance updates correctly`() {
        // Given
        val expectedBalance = 21000000.0

        // When
        walletService.receiveFunds(validWalletId, maximumValueUtxo)

        // Then
        assertEquals(expectedBalance, walletService.getBalance(validWalletId))
    }
}
