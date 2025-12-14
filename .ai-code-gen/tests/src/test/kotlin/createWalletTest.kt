package com.example.bitcoin.test

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import com.example.bitcoin.WalletService
import com.example.bitcoin.Wallet

class WalletServiceTest {
    private lateinit var walletService: WalletService

    @BeforeEach
    fun setUp() {
        walletService = WalletService()
    }

    @Test
    fun `Functional_CreateWalletWithValidLabel`() {
        val label = "Savings"
        val wallet = walletService.createWallet(label)

        assertEquals(label, wallet.label)
        assertEquals(0, wallet.balanceSats)
        assertEquals(0, wallet.utxos.size)
        assert(wallet.address.value.startsWith("bc1"))
    }

    @Test
    fun `Functional_CreateWalletWithEmptyLabel`() {
        assertThrows(IllegalArgumentException::class.java) {
            walletService.createWallet("")
        }
    }

    @Test
    fun `Functional_CreateWalletWithLongLabel`() {
        val longLabel = "a".repeat(256)
        assertThrows(IllegalArgumentException::class.java) {
            walletService.createWallet(longLabel)
        }
    }

    @Test
    fun `Functional_CreateMultipleWalletsEnsureUniqueAddresses`() {
        val labels = listOf("Wallet1", "Wallet2", "Wallet3")
        val wallets = labels.map { walletService.createWallet(it) }

        val addresses = wallets.map { it.address.value }
        assertEquals(addresses.size, addresses.toSet().size)
    }

    @Test
    fun `Functional_CreateWalletWithSpecialCharactersInLabel`() {
        val label = "Special!@#%&*()"
        val wallet = walletService.createWallet(label)

        assertEquals(label, wallet.label)
    }
    
    @Test
    fun `Invariant_VerifyWalletBalanceInitialization`() {
        val wallet = walletService.createWallet("Test")
        assertEquals(0, wallet.balanceSats)
    }

    @Test
    fun `Invariant_VerifyUTXOInitialization`() {
        val wallet = walletService.createWallet("Test")
        assertEquals(0, wallet.utxos.size)
    }

    @Test
    fun `Invariant_VerifyWalletAddressValidity`() {
        val wallet = walletService.createWallet("Test")
        assert(wallet.address.value.startsWith("bc1"))
        assertEquals(42, wallet.address.value.length)
    }

    @Test
    fun `DomainBoundary_CreateWallet_LabelMaxLength`() {
        val maxLabel = "a".repeat(255)
        val wallet = walletService.createWallet(maxLabel)

        assertEquals(maxLabel, wallet.label)
    }

    @Test
    fun `DomainBoundary_CreateWallet_LabelSpecialCharacters`() {
        val label = "Speci@lChar$"
        val wallet = walletService.createWallet(label)

        assertEquals(label, wallet.label)
    }
}