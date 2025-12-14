package com.example.bitcoin.tests

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import com.example.bitcoin.FeeEstimator

class FeeEstimatorTest {

    private lateinit var feeEstimator: FeeEstimator

    @BeforeEach
    fun setUp() {
        // Default setup for tests
        feeEstimator = FeeEstimator()
    }

    @Test
    fun `Functional_EstimateFee_DefaultFeeRate_100000Sats`() {
        // Given
        val satsPerVbyte = 5L
        val amountSats = 100000L
        feeEstimator = FeeEstimator(satsPerVbyte)

        // When
        val estimatedFee = feeEstimator.estimateFee(amountSats)

        // Then
        assertEquals(1200L, estimatedFee)
    }

    @Test
    fun `Functional_EstimateFee_CustomFeeRate_100000Sats`() {
        // Given
        val satsPerVbyte = 10L
        val amountSats = 100000L

        // When
        feeEstimator = FeeEstimator(satsPerVbyte)
        val estimatedFee = feeEstimator.estimateFee(amountSats)

        // Then
        assertEquals(2000L, estimatedFee)
    }

    @Test
    fun `Functional_EstimateFee_ZeroTransactionAmount`() {
        // Given
        val amountSats = 0L

        // When
        val estimatedFee = feeEstimator.estimateFee(amountSats)

        // Then
        assertEquals(1000L, estimatedFee)
    }

    @Test
    fun `Functional_EstimateFee_HighTransactionAmount`() {
        // Given
        val amountSats = 1000000000L

        // When
        val estimatedFee = feeEstimator.estimateFee(amountSats)

        // Then
        assertEquals(1001000L, estimatedFee)
    }

    @Test
    fun `Functional_EstimateFee_NegativeTransactionAmount`() {
        // Given
        val amountSats = -100000L

        // When & Then
        val exception = assertThrows(IllegalArgumentException::class.java) {
            feeEstimator.estimateFee(amountSats)
        }
        assertEquals("Invalid transaction amount", exception.message)
    }

    @Test
    fun `Functional_EstimateFee_MinimumFeeRate`() {
        // Given
        val satsPerVbyte = 1L
        val amountSats = 50000L

        // When
        feeEstimator = FeeEstimator(satsPerVbyte)
        val estimatedFee = feeEstimator.estimateFee(amountSats)

        // Then
        assertEquals(250L, estimatedFee)
    }

    @Test
    fun `Functional_EstimateFee_MaximumFeeRate`() {
        // Given
        val satsPerVbyte = 1000L
        val amountSats = 50000L

        // When
        feeEstimator = FeeEstimator(satsPerVbyte)
        val estimatedFee = feeEstimator.estimateFee(amountSats)

        // Then
        assertEquals(200050L, estimatedFee)
    }

    @Test
    fun `Invariant_VerifyFeeEstimation_AmountNonNegative`() {
        // Given
        val amountSats = -500L

        // When & Then
        val exception = assertThrows(IllegalArgumentException::class.java) {
            feeEstimator.estimateFee(amountSats)
        }
        assertEquals("Invalid transaction amount", exception.message)
    }

    @Test
    fun `Invariant_VerifyFeeEstimation_ValidFeeCalculation`() {
        // Given
        val satsPerVbyte = 5L
        val amountSats = 200000L

        // When
        feeEstimator = FeeEstimator(satsPerVbyte)
        val estimatedFee = feeEstimator.estimateFee(amountSats)

        // Then
        assertEquals(1200L, estimatedFee)
    }
}