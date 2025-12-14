package com.example.bitcoin.tests

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import com.example.bitcoin.WalletService
import com.example.bitcoin.Transaction
import com.example.bitcoin.Address
import com.example.bitcoin.TransactionInput
import com.example.bitcoin.TransactionOutput

class WalletServiceTest {

    private val walletService = WalletService()

    @Test
    fun `Given sufficient balance and UTXOs When sendFunds is called Then transaction is created successfully`() {
        // Given
        val walletId = "12345"
        val toAddress = Address("1BitcoinAddress")
        val amountSats = 50000L

        // When
        val transaction = walletService.sendFunds(walletId, toAddress, amountSats)

        // Then
        assertEquals(1, transaction.outputs.size)
        assertEquals(toAddress, transaction.outputs[0].address)
        assertEquals(amountSats, transaction.outputs[0].amountSats)
    }

    @Test
    fun `Given insufficient UTXOs When sendFunds is called Then error is thrown`() {
        // Given
        val walletId = "12345"
        val toAddress = Address("1BitcoinAddress")
        val amountSats = 100000L

        // When & Then
        val exception = assertThrows<IllegalArgumentException> {
            walletService.sendFunds(walletId, toAddress, amountSats)
        }

        assertEquals("Insufficient spendable UTXOs", exception.message)
    }

    @Test
    fun `Given non-existent wallet When sendFunds is called Then error is thrown`() {
        // Given
        val walletId = "99999"
        val toAddress = Address("1BitcoinAddress")
        val amountSats = 50000L

        // When & Then
        val exception = assertThrows<IllegalArgumentException> {
            walletService.sendFunds(walletId, toAddress, amountSats)
        }

        assertEquals("Wallet not found", exception.message)
    }

    @Test
    fun `Given exact balance minus fees When sendFunds is called Then transaction is created successfully`() {
        // Given
        val walletId = "12345"
        val toAddress = Address("1BitcoinAddress")
        val amountSats = 49500L

        // When
        val transaction = walletService.sendFunds(walletId, toAddress, amountSats)

        // Then
        assertEquals(1, transaction.outputs.size)
        assertEquals(toAddress, transaction.outputs[0].address)
        assertEquals(amountSats, transaction.outputs[0].amountSats)
    }

    @Test
    fun `Given overlapping UTXOs When sendFunds is called Then only available UTXOs are selected`() {
        // Given
        val walletId = "12345"
        val toAddress = Address("1BitcoinAddress")
        val amountSats = 20000L

        // When
        val transaction = walletService.sendFunds(walletId, toAddress, amountSats)

        // Then
        assertEquals(1, transaction.outputs.size)
        assertEquals(toAddress, transaction.outputs[0].address)
        assertEquals(amountSats, transaction.outputs[0].amountSats)
    }

    @Test
    fun `Given zero fee condition When sendFunds is called Then transaction is created with zero fees`() {
        // Given
        val walletId = "12345"
        val toAddress = Address("1BitcoinAddress")
        val amountSats = 50000L

        // When
        val transaction = walletService.sendFunds(walletId, toAddress, amountSats)

        // Then
        assertEquals(1, transaction.outputs.size)
        assertEquals(toAddress, transaction.outputs[0].address)
        assertEquals(amountSats, transaction.outputs[0].amountSats)
    }

    @Test
    fun `Given invalid address format When sendFunds is called Then error is thrown`() {
        // Given
        val walletId = "12345"
        val toAddress = Address("InvalidAddress")
        val amountSats = 50000L

        // When & Then
        val exception = assertThrows<IllegalArgumentException> {
            walletService.sendFunds(walletId, toAddress, amountSats)
        }

        assertEquals("Invalid address format", exception.message)
    }

    @Test
    fun `Given blank or invalid addresses When sendFunds is called Then error is thrown`() {
        // Given
        val walletId = "12345"
        val toAddress = Address("")
        val amountSats = 50000L

        // When & Then
        val exception = assertThrows<IllegalArgumentException> {
            walletService.sendFunds(walletId, toAddress, amountSats)
        }

        assertEquals("Invalid address format", exception.message)
    }
}
