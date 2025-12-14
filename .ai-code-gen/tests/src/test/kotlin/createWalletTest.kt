package com.example.bitcoin.testing

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import com.example.bitcoin.WalletService
import com.example.bitcoin.Address
import com.example.bitcoin.Wallet
import com.example.bitcoin.PrivateKey

class WalletService_CreateWalletTest {

    private lateinit var walletService: WalletService

    @BeforeEach
    fun setUp() {
        walletService = WalletService()
    }

    @Test
    fun `Given a valid label, When creating a wallet, Then it should initialize correctly`() {
        // Given
        val label = "My Wallet"

        // When
        val wallet = walletService.createWallet(label)

        // Then
        assertEquals("My Wallet", wallet.label)
        assertTrue(wallet.address.value.startsWith("bc1"))
        assertEquals(0, wallet.balanceSats)
    }

    @Test
    fun `Given an empty label, When creating a wallet, Then it should throw an error`() {
        // Given
        val emptyLabel = ""

        // When & Then
        val exception = assertThrows<IllegalArgumentException> {
            walletService.createWallet(emptyLabel)
        }
        assertEquals("Label is required", exception.message)
    }

    @Test
    fun `Given a label exceeding character limit, When creating a wallet, Then it should throw an error`() {
        // Given
        val longLabel = "A".repeat(256)

        // When & Then
        val exception = assertThrows<IllegalArgumentException> {
            walletService.createWallet(longLabel)
        }
        assertEquals("Label is too long", exception.message)
    }

    @Test
    fun `Given different labels, When creating wallets, Then each address should be unique`() {
        // Given
        val label1 = "Alice Wallet"
        val label2 = "Bob Wallet"

        // When
        val wallet1 = walletService.createWallet(label1)
        val wallet2 = walletService.createWallet(label2)

        // Then
        assertNotEquals(wallet1.address.value, wallet2.address.value)
    }

    @Test
    fun `Given a wallet creation limit, When exceeding it, Then it should throw an error`() {
        // Given
        val maxWalletCount = 1000000
        repeat(maxWalletCount) { walletService.createWallet("Wallet $it") }

        // When & Then
        val exception = assertThrows<IllegalStateException> {
            walletService.createWallet("Exceed Wallet")
        }
        assertEquals("Wallet limit reached", exception.message)
    }

    @Test
    fun `Given a valid label, When creating a wallet, Then key pair should be generated correctly`() {
        // Given
        val label = "Secure Wallet"

        // When
        val wallet = walletService.createWallet(label)

        // Then
        assertNotNull(wallet.publicKey)
        assertNotNull(wallet.publicKey.value)
    }

    @Test
    fun `Given multiple wallets, When creating, Then all addresses should start with bc1`() {
        // Given
        val labels = listOf("Wallet One", "Wallet Two", "Wallet Three")

        // When
        val wallets = labels.map { walletService.createWallet(it) }

        // Then
        wallets.forEach { assertTrue(it.address.value.startsWith("bc1")) }
    }
}
