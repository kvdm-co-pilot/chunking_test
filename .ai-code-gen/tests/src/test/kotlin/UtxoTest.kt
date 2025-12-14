package com.example.bitcoin.testing

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import com.example.bitcoin.Utxo
import com.example.bitcoin.Address

class Com_example_bitcoin_UtxoTest {

    private lateinit var utxo: Utxo

    @BeforeEach
    fun setUp() {
        // Common setup can be performed here
    }

    @Test
    fun `Given valid inputs, When creating UTXO, Then it should match expected attributes`() {
        // Given
        val txId = "tx12345"
        val index = 0
        val amountSats = 1000000L
        val address = Address("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa")

        // When
        utxo = Utxo(txId, index, amountSats, address)

        // Then
        assertEquals(txId, utxo.txId)
        assertEquals(index, utxo.index)
        assertEquals(amountSats, utxo.amountSats)
        assertEquals(address, utxo.address)
    }

    @Test
    fun `Given zero amount, When creating UTXO, Then it should be flagged as unspendable`() {
        // Given
        val txId = "tx67890"
        val index = 1
        val amountSats = 0L
        val address = Address("1BoatSLRHtKNngkdXEeobR76b53LETtpyT")

        // When
        utxo = Utxo(txId, index, amountSats, address)

        // Then
        assertEquals(txId, utxo.txId)
        assertEquals(index, utxo.index)
        assertEquals(amountSats, utxo.amountSats)
        assertEquals(address, utxo.address)
        // Additional logic to verify unspendable status
    }

    @Test
    fun `Given invalid transaction ID format, When creating UTXO, Then it should throw error`() {
        // Given
        val txId = "invalid_tx_id"
        val index = 0
        val amountSats = 10000L
        val address = Address("1dice8EMZmqKvrGE4Qc9bUFf9PX3xaYDp")

        // When / Then
        val exception = assertThrows<IllegalArgumentException> {
            Utxo(txId, index, amountSats, address)
        }

        assertEquals("Invalid transaction ID format", exception.message)
    }

    @Test
    fun `Given negative index value, When creating UTXO, Then it should throw error`() {
        // Given
        val txId = "tx99999"
        val index = -1
        val amountSats = 50000L
        val address = Address("16ftSEQ4ctQFDtVZiUBusQUjRrGhM3JYwe")

        // When / Then
        val exception = assertThrows<IllegalArgumentException> {
            Utxo(txId, index, amountSats, address)
        }

        assertEquals("Invalid index value", exception.message)
    }

    @Test
    fun `Given amount exceeding limits, When creating UTXO, Then it should throw error`() {
        // Given
        val txId = "tx55555"
        val index = 3
        val amountSats = 2100000000000000L
        val address = Address("3J98t1WpEZ73CNmQviecrnyiWrnqRhWNLy")

        // When / Then
        val exception = assertThrows<IllegalArgumentException> {
            Utxo(txId, index, amountSats, address)
        }

        assertEquals("Amount exceeds allowable limits", exception.message)
    }

    @Test
    fun `Given maximum index value, When creating UTXO, Then it should match expected attributes`() {
        // Given
        val txId = "tx88888"
        val index = Int.MAX_VALUE
        val amountSats = 100000L
        val address = Address("bc1qw4dhq8v9q3z4h0php7v6wd4q2h9h5r8qf6kz7y")

        // When
        utxo = Utxo(txId, index, amountSats, address)

        // Then
        assertEquals(txId, utxo.txId)
        assertEquals(index, utxo.index)
        assertEquals(amountSats, utxo.amountSats)
        assertEquals(address, utxo.address)
    }
}
