package com.example.bitcoin.test

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import com.example.bitcoin.FeeEstimator

class FeeEstimatorTest {

    private lateinit var feeEstimator: FeeEstimator
    private val satsPerVbyte = 5L // Example value, adjust as needed

    @BeforeEach
    fun setUp() {
        feeEstimator = FeeEstimator()
    }

    @Test
    fun `Functional_ValidTransactionAmount_500000Sats`() {
        val fee = feeEstimator.estimateFee(500000L)
        val expectedFee = 200L * satsPerVbyte + (500000L / 1000)
        assertEquals(expectedFee, fee)
    }

    @Test
    fun `Functional_MinTransactionAmount_1Sat`() {
        val fee = feeEstimator.estimateFee(1L)
        val expectedFee = 200L * satsPerVbyte + (1L / 1000)
        assertEquals(expectedFee, fee)
    }

    @Test
    fun `Functional_ZeroTransactionAmount_0Sats`() {
        val fee = feeEstimator.estimateFee(0L)
        val expectedFee = 200L * satsPerVbyte
        assertEquals(expectedFee, fee)
    }

    @Test
    fun `Functional_MaxTransactionAmount_MaxSupply`() {
        val fee = feeEstimator.estimateFee(2100000000000000L)
        val expectedFee = 200L * satsPerVbyte + (2100000000000000L / 1000)
        assertEquals(expectedFee, fee)
    }

    @Test
    fun `Functional_NegativeTransactionAmount_ErrorThrown`() {
        assertThrows(IllegalArgumentException::class.java) {
            feeEstimator.estimateFee(-1000L)
        }
    }

    @Test
    fun `Boundary_TransactionAmount_DustLimit`() {
        val fee = feeEstimator.estimateFee(546L)
        val expectedFee = 200L * satsPerVbyte + (546L / 1000)
        assertEquals(expectedFee, fee)
    }

    @Test
    fun `Boundary_FeeEstimation_MinRelayFee`() {
        val fee = feeEstimator.estimateFee(1000L)
        val expectedFee = 200L * 5L + (1000L / 1000)
        assertEquals(expectedFee, fee)
    }

    @Test
    fun `Boundary_FeeEstimation_VariableNetworkCongestion`() {
        // Assuming network conditions alter satsPerVbyte dynamically
        val fee = feeEstimator.estimateFee(500000L)
        val expectedFee = 200L * satsPerVbyte + (500000L / 1000)
        assertEquals(expectedFee, fee)
    }
}
