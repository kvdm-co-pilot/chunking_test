package com.example.bitcoin.testing

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import com.example.bitcoin.FeeEstimator

class Com_example_bitcoin_EstimateFeeTest {

    private lateinit var feeEstimator: FeeEstimator
    private val satsPerVbyte = 5L

    @BeforeEach
    fun setUp() {
        feeEstimator = FeeEstimator()
    }

    @Test
    fun `Estimate fee for standard transaction amount`() {
        // Given
        val amountSats = 10_000L
        val expectedFee = 200 * satsPerVbyte + (amountSats / 1000) // Expected: 200 * 5 + (10000 / 1000) = 1050
        
        // When
        val actualFee = feeEstimator.estimateFee(amountSats)

        // Then
        assertEquals(expectedFee, actualFee)
    }

    @Test
    fun `Estimate fee for zero transaction amount`() {
        // Given
        val amountSats = 0L
        val expectedFee = 200 * satsPerVbyte + (amountSats / 1000) // Expected: 200 * 5 + (0 / 1000) = 1000

        // When
        val actualFee = feeEstimator.estimateFee(amountSats)

        // Then
        assertEquals(expectedFee, actualFee)
    }

    @Test
    fun `Estimate fee for large transaction amount`() {
        // Given
        val amountSats = 1_000_000L
        val expectedFee = 200 * satsPerVbyte + (amountSats / 1000) // Expected: 200 * 5 + (1000000 / 1000) = 2000

        // When
        val actualFee = feeEstimator.estimateFee(amountSats)

        // Then
        assertEquals(expectedFee, actualFee)
    }

    @Test
    fun `Estimate fee with negative transaction amount should throw error`() {
        // Given
        val amountSats = -1_000L

        // When & Then
        val exception = assertThrows<IllegalArgumentException> {
            feeEstimator.estimateFee(amountSats)
        }
        assertEquals("Invalid transaction amount", exception.message)
    }

    @Test
    fun `Estimate fee with maximum allowable transaction amount`() {
        // Given
        val amountSats = Long.MAX_VALUE
        val expectedFee = 200 * satsPerVbyte + (amountSats / 1000) // Calculate expected value without overflow
        
        // When
        val actualFee = feeEstimator.estimateFee(amountSats)

        // Then
        assertEquals(expectedFee, actualFee)
    }
}
