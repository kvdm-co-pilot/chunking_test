package com.example.bitcoin.testing

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import com.example.bitcoin.Utxo
import com.example.bitcoin.Address

class Com_example_bitcoin_UtxoTest {

    private lateinit var validAddress: Address

    @BeforeEach
    fun setUp() {
        validAddress = Address("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa")
    }

    @Test
    fun `Functional_ValidUtxoCreation`() {
        // Given
        val txId = "a1b2c3d4e5f6"
        val index = 0
        val amountSats = 5000000000L

        // When
        val utxo = Utxo(txId, index, amountSats, validAddress)

        // Then
        assertEquals(txId, utxo.txId)
        assertEquals(index, utxo.index)
        assertEquals(amountSats, utxo.amountSats)
        assertEquals(validAddress, utxo.address)
    }

    @Test
    fun `Functional_InvalidTransactionId_UtxoCreation`() {
        // Given
        val invalidTxId = "!!!incorrect!!!"
        val index = 1
        val amountSats = 100000000L

        // Expect
        val exception = assertThrows<IllegalArgumentException> {
            Utxo(invalidTxId, index, amountSats, validAddress)
        }

        // Then
        assertEquals("Invalid transaction ID format", exception.message)
    }

    @Test
    fun `Functional_NegativeAmount_UtxoCreation`() {
        // Given
        val txId = "b2c3d4e5f6a1"
        val index = 1
        val negativeAmountSats = -5000000000L

        // Expect
        val exception = assertThrows<IllegalArgumentException> {
            Utxo(txId, index, negativeAmountSats, validAddress)
        }

        // Then
        assertEquals("Amount must be non-negative", exception.message)
    }

    @Test
    fun `Functional_MaximumSatoshis_UtxoCreation`() {
        // Given
        val txId = "c3d4e5f6a1b2"
        val index = 2
        val maxAmountSats = 2100000000000000L

        // When
        val utxo = Utxo(txId, index, maxAmountSats, validAddress)

        // Then
        assertEquals(maxAmountSats, utxo.amountSats)
    }

    @Test
    fun `Functional_EdgeCaseIndex_UtxoCreation`() {
        // Given
        val txId = "d4e5f6a1b2c3"
        val edgeCaseIndex = 4294967295
        val amountSats = 200000000L

        // When
        val utxo = Utxo(txId, edgeCaseIndex, amountSats, validAddress)

        // Then
        assertEquals(edgeCaseIndex, utxo.index)
    }

    @Test
    fun `Functional_InvalidAddress_UtxoCreation`() {
        // Given
        val txId = "e5f6a1b2c3d4"
        val index = 3
        val amountSats = 150000000L
        val invalidAddress = Address("invalid_address")

        // Expect
        val exception = assertThrows<IllegalArgumentException> {
            Utxo(txId, index, amountSats, invalidAddress)
        }

        // Then
        assertEquals("Invalid address format", exception.message)
    }

    @Test
    fun `Functional_MultipleUtxosUniqueIndices`() {
        // Given
        val txId = "f6a1b2c3d4e5"
        val indices = listOf(0, 1, 2)
        val amountSats = 100000000L

        // When
        val utxo0 = Utxo(txId, indices[0], amountSats, validAddress)
        val utxo1 = Utxo(txId, indices[1], amountSats, validAddress)
        val utxo2 = Utxo(txId, indices[2], amountSats, validAddress)

        // Then
        assertEquals(indices[0], utxo0.index)
        assertEquals(indices[1], utxo1.index)
        assertEquals(indices[2], utxo2.index)
    }
}
