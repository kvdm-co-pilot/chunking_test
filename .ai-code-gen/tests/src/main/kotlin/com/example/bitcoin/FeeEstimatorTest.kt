package com.example.bitcoin.tests

import com.example.bitcoin.FeeEstimator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class FeeEstimatorTest {

    private lateinit var feeEstimator: FeeEstimator

    @BeforeEach
    fun setup() {
        feeEstimator = FeeEstimator()
    }

    @Test
    fun `Functional_EstimateFeeStandardTransaction`() {
        // Given
        val amountSats = 10000L
        val expectedFee = 1200L

        // When
        val actualFee = feeEstimator.estimateFee(amountSats)

        // Then
        assertEquals(expectedFee, actualFee)
    }

    @Test
    fun `Functional_EstimateFeeHighTransactionAmount`() {
        // Given
        val amountSats = 1000000L
        val expectedFee = 1200L

        // When
        val actualFee = feeEstimator.estimateFee(amountSats)

        // Then
        assertEquals(expectedFee, actualFee)
    }

    @Test
    fun `Functional_EstimateFeeZeroTransactionAmount`() {
        // Given
        val amountSats = 0L
        val expectedFee = 1000L

        // When
        val actualFee = feeEstimator.estimateFee(amountSats)

        // Then
        assertEquals(expectedFee, actualFee)
    }

    @Test
    fun `Functional_EstimateFeeMaximumFeeRate`() {
        // Given
        val amountSats = 50000L
        val feeEstimator = FeeEstimator(satsPerVbyte = 100)
        val expectedFee = 20050L

        // When
        val actualFee = feeEstimator.estimateFee(amountSats)

        // Then
        assertEquals(expectedFee, actualFee)
    }

    @Test
    fun `Functional_EstimateFeeMinimumFeeRate`() {
        // Given
        val amountSats = 50000L
        val feeEstimator = FeeEstimator(satsPerVbyte = 1)
        val expectedFee = 1050L

        // When
        val actualFee = feeEstimator.estimateFee(amountSats)

        // Then
        assertEquals(expectedFee, actualFee)
    }

    @Test
    fun `Functional_EstimateFeeEdgeCaseTransactionAmount`() {
        // Given
        val amountSats = 999L
        val expectedFee = 1000L

        // When
        val actualFee = feeEstimator.estimateFee(amountSats)

        // Then
        assertEquals(expectedFee, actualFee)
    }

    @Test
    fun `Functional_EstimateFeeNegativeFeeRate`() {
        // Given
        val amountSats = 10000L

        // When & Then
        assertThrows(IllegalArgumentException::class.java) {
            FeeEstimator(satsPerVbyte = -5).estimateFee(amountSats)
        }
    }

    @Test
    fun `Functional_EstimateFeeNegativeTransactionAmount`() {
        // Given
        val amountSats = -10000L

        // When & Then
        assertThrows(IllegalArgumentException::class.java) {
            feeEstimator.estimateFee(amountSats)
        }
    }

    // Additional tests for the remaining test plan scenarios can be implemented similarly.
}
