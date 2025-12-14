package com.example.bitcoin.testing

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import com.example.bitcoin.FeeEstimator

class EstimateFeeTest {

    private lateinit var feeEstimator: FeeEstimator

    @BeforeEach
    fun setUp() {
        feeEstimator = FeeEstimator()
    }

    @Test
    fun `Functional_EstimateFeeWithDefaultRate_100kSats`() {
        // Given
        val satsPerVbyte = 5L
        val amountSats = 100000L
        val expectedFee = 1200L // Expected: 200 * 5 + (100000 / 1000) = 1000 + 200 = 1200

        // When
        val actualFee = feeEstimator.estimateFee(amountSats)

        // Then
        assertEquals(expectedFee, actualFee)
    }

    @Test
    fun `Functional_EstimateFeeWithCustomRate_10SatsPerVbyte_50kSats`() {
        // Given
        feeEstimator = FeeEstimator(10L)
        val amountSats = 50000L
        val expectedFee = 2700L // Expected: 200 * 10 + (50000 / 1000) = 2000 + 500 = 2700

        // When
        val actualFee = feeEstimator.estimateFee(amountSats)

        // Then
        assertEquals(expectedFee, actualFee)
    }

    @Test
    fun `Boundary_EstimateFee_ZeroAmount`() {
        // Given
        val amountSats = 0L
        val expectedFee = 1000L // Expected: 200 * 5 + (0 / 1000) = 1000 + 0 = 1000

        // When
        val actualFee = feeEstimator.estimateFee(amountSats)

        // Then
        assertEquals(expectedFee, actualFee)
    }

    @Test
    fun `Boundary_EstimateFee_MinimumNonZeroAmount`() {
        // Given
        val amountSats = 1L
        val expectedFee = 1000L // Expected: 200 * 5 + (1 / 1000) = 1000 + 0 = 1000

        // When
        val actualFee = feeEstimator.estimateFee(amountSats)

        // Then
        assertEquals(expectedFee, actualFee)
    }

    @Test
    fun `Boundary_EstimateFee_MaximumLongValue`() {
        // Given
        val amountSats = Long.MAX_VALUE
        val expectedFee = 9223372036854776807L // Expected: 200 * 5 + (9223372036854775807 / 1000)

        // When
        val actualFee = feeEstimator.estimateFee(amountSats)

        // Then
        assertEquals(expectedFee, actualFee)
    }

    @Test
    fun `Functional_EstimateFee_HighFeeRate`() {
        // Given
        feeEstimator = FeeEstimator(100L)
        val amountSats = 10000L
        val expectedFee = 20100L // Expected: 200 * 100 + (10000 / 1000) = 20000 + 100 = 20100

        // When
        val actualFee = feeEstimator.estimateFee(amountSats)

        // Then
        assertEquals(expectedFee, actualFee)
    }

    @Test
    fun `Error_EstimateFee_NegativeFeeRate`() {
        // Given
        val satsPerVbyte = -5L

        // Then
        val exception = assertThrows(IllegalArgumentException::class.java) {
            FeeEstimator(satsPerVbyte)
        }
        assertEquals("Invalid fee rate", exception.message)
    }
}