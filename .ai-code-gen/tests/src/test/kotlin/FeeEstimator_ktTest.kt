package com.example.bitcoin.test

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import com.example.bitcoin.FeeEstimator

class FeeEstimatorTest {
    private lateinit var feeEstimator: FeeEstimator

    @BeforeEach
    fun setUp() {
        feeEstimator = FeeEstimator(5)
    }

    @Test
    fun `Given a standard transaction When estimating fee Then it should return the expected fee`() {
        val amountSats = 10000L
        val expectedFee = 1005L
        assertEquals(expectedFee, feeEstimator.estimateFee(amountSats))
    }

    @Test
    fun `Given a zero amount transaction When estimating fee Then it should return the base fee`() {
        val amountSats = 0L
        val expectedFee = 1000L
        assertEquals(expectedFee, feeEstimator.estimateFee(amountSats))
    }

    @Test
    fun `Given a maximum transaction amount When estimating fee Then it should handle large values`() {
        val amountSats = Long.MAX_VALUE
        val expectedFee = 9223372036854777807L // 200 * 5 + (Long.MAX_VALUE / 1000)
        assertEquals(expectedFee, feeEstimator.estimateFee(amountSats))
    }

    @Test
    fun `Given a minimum satsPerVbyte When estimating fee Then it should return the calculated fee`() {
        feeEstimator = FeeEstimator(0)
        val amountSats = 10000L
        val expectedFee = 10L
        assertEquals(expectedFee, feeEstimator.estimateFee(amountSats))
    }

    @Test
    fun `Given a negative satsPerVbyte When estimating fee Then it should not return a negative fee`() {
        feeEstimator = FeeEstimator(-5)
        val amountSats = 10000L
        val expectedFee = 0L // Fee should not be negative
        assertEquals(expectedFee, feeEstimator.estimateFee(amountSats))
    }

    @Test
    fun `Given a high satsPerVbyte When estimating fee Then it should reflect the high rate`() {
        feeEstimator = FeeEstimator(1000)
        val amountSats = 10000L
        val expectedFee = 200000L // 200 * 1000 + (10000 / 1000)
        assertEquals(expectedFee, feeEstimator.estimateFee(amountSats))
    }

    @Test
    fun `Given a large transaction size When estimating fee Then it should proportionally increase`() {
        feeEstimator = FeeEstimator(5)
        val amountSats = 10000L
        val transactionSizeVbytes = 1000L
        val expectedFee = transactionSizeVbytes * 5 + (amountSats / 1000)
        assertEquals(expectedFee, feeEstimator.estimateFee(amountSats))
    }
}
