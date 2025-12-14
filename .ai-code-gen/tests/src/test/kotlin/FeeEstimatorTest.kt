package com.example.bitcoin.test

import org.junit.jupiter.api.Assertions.assertEquals
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
    fun `Given default rate and standard transaction amount When estimating fee Then it should match exact expectation`() {
        val estimatedFee = feeEstimator.estimateFee(1000L)
        assertEquals(1005L, estimatedFee)
    }

    @Test
    fun `Given custom rate and standard transaction amount When estimating fee Then it should match exact expectation`() {
        feeEstimator = FeeEstimator(10)
        val estimatedFee = feeEstimator.estimateFee(2000L)
        assertEquals(2020L, estimatedFee)
    }

    @Test
    fun `Given zero amount When estimating fee Then it should match expected minimum fee`() {
        val estimatedFee = feeEstimator.estimateFee(0L)
        assertEquals(1000L, estimatedFee)
    }

    @Test
    fun `Given very large transaction amount When estimating fee Then it should match expected fee`() {
        val estimatedFee = feeEstimator.estimateFee(10000000L)
        assertEquals(11000L, estimatedFee)
    }

    @Test
    fun `Given minimum transaction size When estimating fee Then it should match expected minimum fee`() {
        val estimatedFee = feeEstimator.estimateFee(1L)
        assertEquals(1000L, estimatedFee)
    }

    @Test
    fun `Given no default rate When estimating fee Then it should match expected minimum fee`() {
        feeEstimator = FeeEstimator(0)
        val estimatedFee = feeEstimator.estimateFee(1000L)
        assertEquals(1L, estimatedFee)
    }

    @Test
    fun `Given dust limit amount When estimating fee Then it should match expected minimum fee`() {
        val estimatedFee = feeEstimator.estimateFee(546L)
        assertEquals(1000L, estimatedFee)
    }

    @Test
    fun `Given maximum Bitcoin supply When estimating fee Then it should match expected fee`() {
        val estimatedFee = feeEstimator.estimateFee(2100000000000000L)
        assertEquals(210000000001000L, estimatedFee)
    }

    @Test
    fun `Given minimum relay fee rate When estimating fee Then it should match expected fee`() {
        feeEstimator = FeeEstimator(1)
        val estimatedFee = feeEstimator.estimateFee(1000L)
        assertEquals(201L, estimatedFee)
    }
}