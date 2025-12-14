package com.example.bitcoin.testing

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import com.example.bitcoin.FeeEstimator

class FeeEstimatorTest {

    private lateinit var feeEstimator: FeeEstimator

    @BeforeEach
    fun setUp() {
        feeEstimator = FeeEstimator()
    }

    @Test
    fun `Given default satsPerVbyte, When calculating fee for 100000 satoshis, Then fee should be 1200 satoshis`() {
        // Given
        val amountSats = 100000L
        val expectedFee = 1200L // Expected: 200 * 5 + (100000 / 1000) = 1000 + 100 = 1200

        // When
        val actualFee = feeEstimator.estimateFee(amountSats)

        // Then
        assertEquals(expectedFee, actualFee)
    }

    @Test
    fun `Given custom satsPerVbyte rate, When calculating fee for 50000 satoshis, Then fee should be 2500 satoshis`() {
        // Given
        val amountSats = 50000L
        feeEstimator = FeeEstimator(10L)
        val expectedFee = 2500L // Expected: 200 * 10 + (50000 / 1000) = 2000 + 50 = 2500

        // When
        val actualFee = feeEstimator.estimateFee(amountSats)

        // Then
        assertEquals(expectedFee, actualFee)
    }

    @Test
    fun `Given zero transaction amount, When calculating fee, Then fee should be 1000 satoshis`() {
        // Given
        val amountSats = 0L
        val expectedFee = 1000L // Expected: 200 * 5 + (0 / 1000) = 1000 + 0 = 1000

        // When
        val actualFee = feeEstimator.estimateFee(amountSats)

        // Then
        assertEquals(expectedFee, actualFee)
    }

    @Test
    fun `Given minimum non-zero transaction amount, When calculating fee, Then fee should be 1000 satoshis`() {
        // Given
        val amountSats = 1L
        val expectedFee = 1000L // Expected: 200 * 5 + (1 / 1000) = 1000 + 0 = 1000

        // When
        val actualFee = feeEstimator.estimateFee(amountSats)

        // Then
        assertEquals(expectedFee, actualFee)
    }

    @Test
    fun `Given very high transaction amount, When estimating fee, Then fee should be 1001000 satoshis`() {
        // Given
        val amountSats = 1000000000L
        val expectedFee = 1001000L // Expected: 200 * 5 + (1000000000 / 1000) = 1000 + 1000000 = 1001000

        // When
        val actualFee = feeEstimator.estimateFee(amountSats)

        // Then
        assertEquals(expectedFee, actualFee)
    }

    @Test
    fun `Given extreme satsPerVbyte rate, When estimating fee for 100000 satoshis, Then fee should be extremely high`() {
        // Given
        val amountSats = 100000L
        feeEstimator = FeeEstimator(1000L)
        val expectedFee = 200100L // Expected: 200 * 1000 + (100000 / 1000) = 200000 + 100 = 200100

        // When
        val actualFee = feeEstimator.estimateFee(amountSats)

        // Then
        assertEquals(expectedFee, actualFee)
    }

    @Test
    fun `Given boundary satsPerVbyte rate of zero, When estimating fee for 100000 satoshis, Then fee should be 100 satoshis`() {
        // Given
        val amountSats = 100000L
        feeEstimator = FeeEstimator(0L)
        val expectedFee = 100L // Expected: 200 * 0 + (100000 / 1000) = 0 + 100 = 100

        // When
        val actualFee = feeEstimator.estimateFee(amountSats)

        // Then
        assertEquals(expectedFee, actualFee)
    }

    @Test
    fun `Given transaction amount at dust limit, When estimating fee, Then fee should be slightly above base fee`() {
        // Given
        val amountSats = 546L
        val expectedFee = 1000L // Expected: 200 * 5 + (546 / 1000) = 1000 + 0 = 1000

        // When
        val actualFee = feeEstimator.estimateFee(amountSats)

        // Then
        assertEquals(expectedFee, actualFee)
    }

    @Test
    fun `Given maximum block reward amount, When estimating fee, Then fee should be approximately 625200 satoshis`() {
        // Given
        val amountSats = 625000000L
        val expectedFee = 625200L // Expected: 200 * 5 + (625000000 / 1000) = 1000 + 625000 = 625200

        // When
        val actualFee = feeEstimator.estimateFee(amountSats)

        // Then
        assertEquals(expectedFee, actualFee)
    }

    @Test
    fun `Given transaction amount above minimum relay fee threshold, When estimating fee, Then fee should be minimal`() {
        // Given
        val amountSats = 1000L
        feeEstimator = FeeEstimator(1L)
        val expectedFee = 2001L // Expected: 200 * 1 + (1000 / 1000) = 200 + 1 = 201

        // When
        val actualFee = feeEstimator.estimateFee(amountSats)

        // Then
        assertEquals(expectedFee, actualFee)
    }

    @Test
    fun `Given priority fee, When estimating fee, Then fee should be 3200 satoshis`() {
        // Given
        val amountSats = 100000L
        val priorityFee = 2000L
        val expectedFee = 3200L // Expected: 200 * 5 + (100000 / 1000) + 2000 = 1000 + 100 + 2000 = 3200

        // When
        val actualFee = feeEstimator.estimateFee(amountSats) + priorityFee

        // Then
        assertEquals(expectedFee, actualFee)
    }
}

