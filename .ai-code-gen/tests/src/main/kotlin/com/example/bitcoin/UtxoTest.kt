package com.example.bitcoin.tests

import com.example.bitcoin.Utxo
import com.example.bitcoin.Address
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class UtxoTest {

    private lateinit var validUtxo: Utxo
    private lateinit var edgeCaseUtxo: Utxo
    private lateinit var validAddress: Address

    @BeforeEach
    fun setup() {
        validAddress = Address("1BitcoinAddressExample")
    }

    @Test
    fun `Given valid data When creating UTXO Then it should initialize correctly`() {
        // Given
        val txId = "abc123"
        val index = 0
        val amountSats = 5000000L
        val address = validAddress

        // When
        validUtxo = Utxo(txId, index, amountSats, address)

        // Then
        assertEquals(txId, validUtxo.txId)
        assertEquals(index, validUtxo.index)
        assertEquals(amountSats, validUtxo.amountSats)
        assertEquals(address, validUtxo.address)
    }

    @Test
    fun `Given edge case values When creating UTXO Then it should initialize correctly`() {
        // Given
        val txId = "def456"
        val index = Int.MAX_VALUE
        val amountSats = 0L
        val address = Address("1EdgeCaseBitcoinAddress")

        // When
        edgeCaseUtxo = Utxo(txId, index, amountSats, address)

        // Then
        assertEquals(index, edgeCaseUtxo.index)
        assertEquals(amountSats, edgeCaseUtxo.amountSats)
    }

    @Test
    fun `Given invalid transaction ID When creating UTXO Then it should throw error`() {
        // Given
        val invalidTxId = ""
        val index = 1
        val amountSats = 1000L
        val address = Address("1ValidBitcoinAddress")

        // Then
        assertThrows(IllegalArgumentException::class.java) {
            // When
            Utxo(invalidTxId, index, amountSats, address)
        }
    }

    @Test
    fun `Given negative amount When creating UTXO Then it should throw error`() {
        // Given
        val txId = "xyz789"
        val index = 2
        val amountSats = -1000L
        val address = Address("1AnotherBitcoinAddress")

        // Then
        assertThrows(IllegalArgumentException::class.java) {
            // When
            Utxo(txId, index, amountSats, address)
        }
    }

    @Test
    fun `Given valid transaction ID and address When creating UTXO with boundary amount Then it should initialize correctly`() {
        // Given
        val txId = "ghi789"
        val index = 0
        val amountSats = 1L
        val address = Address("1BoundaryValueAddressExample")

        // When
        val boundaryUtxo = Utxo(txId, index, amountSats, address)

        // Then
        assertEquals(amountSats, boundaryUtxo.amountSats)
    }

    @Test
    fun `Given invalid address prefix When creating UTXO Then it should throw error`() {
        // Given
        val txId = "jkl012"
        val index = 3
        val amountSats = 500L
        val invalidAddress = Address("InvalidPrefixAddress")

        // Then
        assertThrows(IllegalArgumentException::class.java) {
            // When
            Utxo(txId, index, amountSats, invalidAddress)
        }
    }
}