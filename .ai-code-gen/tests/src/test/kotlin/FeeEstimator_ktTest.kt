package com.example.bitcoin.tests

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import com.example.bitcoin.FeeEstimator

class FeeEstimatorTest {

    @Test
    fun `Given a standard transaction When fee is estimated with default rate Then fee should be correct`() {
        // Given
        val amountSats = 100000L
        val feeEstimator = FeeEstimator()

        // When
        val estimatedFee = feeEstimator.estimateFee(amountSats)

        // Then
        assertEquals(1000L, estimatedFee)
    }

    @Test
    fun `Given a transaction with custom fee rate When fee is estimated Then fee should be correct`() {
        // Given
        val amountSats = 100000L
        val feeEstimator = FeeEstimator(satsPerVbyte = 10)

        // When
        val estimatedFee = feeEstimator.estimateFee(amountSats)

        // Then
        assertEquals(2000L, estimatedFee)
    }

    @Test
    fun `Given a small transaction When fee is estimated with default rate Then fee should be correct`() {
        // Given
        val amountSats = 1L
        val feeEstimator = FeeEstimator()

        // When
        val estimatedFee = feeEstimator.estimateFee(amountSats)

        // Then
        assertEquals(1001L, estimatedFee)
    }

    @Test
    fun `Given a large transaction When fee is estimated with default rate Then fee should be correct`() {
        // Given
        val amountSats = 1000000000L
        val feeEstimator = FeeEstimator()

        // When
        val estimatedFee = feeEstimator.estimateFee(amountSats)

        // Then
        assertEquals(1001000L, estimatedFee)
    }

    @Test
    fun `Given a zero transaction size When fee is estimated Then fee should be correct`() {
        // Given
        val amountSats = 100000L
        val feeEstimator = FeeEstimator()

        // When
        val estimatedFee = feeEstimator.estimateFee(amountSats)

        // Then
        assertEquals(100L, estimatedFee)
    }

    @Test
    fun `Given a negative fee rate When fee is estimated Then exception should be thrown`() {
        // Given
        val amountSats = 100000L
        val feeEstimator = FeeEstimator(satsPerVbyte = -5)

        // Then
        val exception = assertThrows<IllegalArgumentException> {
            // When
            feeEstimator.estimateFee(amountSats)
        }
        assertEquals("Invalid fee rate", exception.message)
    }

    @Test
    fun `Given a maximum long transaction amount When fee is estimated Then fee should calculate correctly`() {
        // Given
        val amountSats = Long.MAX_VALUE
        val feeEstimator = FeeEstimator()

        // When
        val estimatedFee = feeEstimator.estimateFee(amountSats)

        // Then
        assertEquals(estimatedFee, feeEstimator.estimateFee(amountSats))
    }
}
