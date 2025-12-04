package com.example.bitcoin

class FeeEstimator(private val satsPerVbyte: Long = 5) {
    fun estimateFee(amountSats: Long): Long {
        val sizeVbytes = 200L
        return sizeVbytes * satsPerVbyte + (amountSats / 1000)
    }
}
